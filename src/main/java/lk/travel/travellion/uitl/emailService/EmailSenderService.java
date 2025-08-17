package lk.travel.travellion.uitl.emailService;

import jakarta.mail.internet.MimeMessage;
import lk.travel.travellion.uitl.emailService.Thymeleaf.MailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

/**
 * A service class for sending both plain text and HTML emails.
 * This service utilizes the {@link JavaMailSender} and integrates with
 * Spring's email capabilities to send emails asynchronously or synchronously.
 * Additionally, it allows the customization of email content using
 * Thymeleaf templates.
 *
 * This class handles two primary email formats:
 * - Plain text emails
 * - HTML emails (rendered using Thymeleaf templates)
 *
 * Dependencies:
 * The service requires proper configuration of the JavaMailSender
 * bean and access to email-related configurations such as the email sender
 * address, which is injected via the application's property source.
 */
@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailTemplateBuilder emailTemplateBuilder;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    /**
     * Sends a plain text email using the provided mail body details. This method validates
     * the mail body, builds the email content using a template, and sends the email via the configured
     * mail sender. If email sending fails, a runtime exception is thrown.
     *
     * @param mailBody the {@code MailBody} object containing email details such as recipient, subject,
     *                 content, and optional fields like greetings and signature
     * @throws IllegalArgumentException if the provided {@code mailBody} does not contain valid data
     *                                  (e.g., missing recipient, subject, or content)
     * @throws RuntimeException         if there is an error during the email sending process
     */
    public void sendMail(MailBody mailBody) {

        mailBody.validate();

        String content = emailTemplateBuilder.buildMailContent(mailBody);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.getTo());
        message.setSubject(mailBody.getSubject());
        message.setText(content);
        message.setFrom(sender);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }

    /**
     * Sends an HTML email to the specified recipient using the provided mailRequest details.
     * The method leverages Thymeleaf templates to build dynamic email content.
     *
     * @param mailRequest the MailRequest object containing email details such as recipient's address,
     *                    subject, recipient name, email content, signature, and the template name to be used.
     *                    It must include:
     *                    - to: the recipient's email address.
     *                    - subject: the subject of the email.
     *                    - recipientName: the name of the recipient to be addressed in the email.
     *                    - content: the main body content of the email.
     *                    - signature: the signature to be appended at the end of the email.
     *                    - templateName: the file name of the Thymeleaf template to generate the email content.
     */
    public void sendHtmlEmail(MailRequest mailRequest) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            // Create the Thymeleaf context and set variables from MailRequest
            Context context = new Context();
            context.setVariable("greeting", mailRequest.getRecipientName());
            context.setVariable("mainContent", mailRequest.getContent());
            context.setVariable("signature", mailRequest.getSignature());

            // Process the template
            String htmlContent = templateEngine.process(mailRequest.getTemplateName(), context);

            helper.setFrom(sender);
            helper.setTo(mailRequest.getTo());
            helper.setSubject(mailRequest.getSubject());
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
