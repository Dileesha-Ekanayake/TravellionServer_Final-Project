package lk.travel.travellion.controller;

import lk.travel.travellion.uitl.emailService.EmailSenderService;
import lk.travel.travellion.uitl.emailService.MailBody;
import lk.travel.travellion.uitl.emailService.Thymeleaf.MailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/emil")
@RequiredArgsConstructor
public class EmailController {


    private final EmailSenderService emailSenderService;

    @GetMapping(path = "/mail")
    public ResponseEntity<String> sendTestMail() {

        MailBody mailBody = MailBody.builder()
                .to("example@gmail.com")
                .subject("Testing Mail Service")
                .greetings("Dear Employee")
                .content("This is a test email sent from our system.")
                .closing("Thank you for your attention")
                .signature("Best regards,\nAdmin")
                .build();

        emailSenderService.sendMail(mailBody);
        return ResponseEntity.ok("Mail sent");
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> sendWelcomeEmail() {
        try {
            MailRequest mailRequest = MailRequest.builder()
                    .to("example@gmail.com")
                    .subject("Welcome to Travellion")
                    .recipientName("Dear Valued Customer")
                    .content("Welcome to Travellion! We're excited to have you on board.")
                    .signature("Best regards,\nThe Travellion IT Team")
                    .templateName("base-template")
                    .build();

            emailSenderService.sendHtmlEmail(mailRequest);

            return ResponseEntity.ok("Welcome email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }

}
