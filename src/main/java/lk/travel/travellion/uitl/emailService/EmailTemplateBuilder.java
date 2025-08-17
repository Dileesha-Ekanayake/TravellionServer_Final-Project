package lk.travel.travellion.uitl.emailService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * A service class for constructing email templates using provided data.
 * This class enables the formatting and assembly of email content, including
 * greetings, main message content, closing remarks, and a signature with company
 * details. All parts of the email can utilize default values if not explicitly
 * provided.
 *
 * The email construction process includes:
 * - Formatting a personalized greeting or using a default fallback.
 * - Adding the email's main content.
 * - Adding closing remarks.
 * - Incorporating a formatted signature including the company name.
 *
 * This class relies on configuration properties for company details,
 * such as the company's name, which is injected at runtime from the application's
 * property source.
 */
@Service
public class EmailTemplateBuilder {

    @Value("${company.name:Research Security}")
    private String companyName;

    /**
     * Builds the content of an email based on the provided MailBody data.
     * The content includes a formatted greeting, main content, closing remarks,
     * and a signature.
     *
     * @param mailBody an object containing the data required for building the email
     *                 including greetings, content, closing, and signature.
     *                 Must not be null and should contain non-empty values for required fields.
     * @return the constructed email content as a string.
     */
    public String buildMailContent(MailBody mailBody){
        StringBuilder emailContent = new StringBuilder();

        // Add greeting with proper formatting
        emailContent.append(formatGreeting(mailBody.getGreetings()))
                .append("\n\n");

        // Add main content
        emailContent.append(mailBody.getContent())
                .append("\n\n");

        // Add closing
        emailContent.append(mailBody.getClosing())
                .append("\n\n");

        // Add signature with company details
        emailContent.append(formatSignature(mailBody.getSignature()));

        return emailContent.toString();

    }

    /**
     * Formats a greeting message by returning the provided greeting if it is non-empty
     * and contains text; otherwise, it returns a default greeting "Dear Valued Customer".
     *
     * @param greeting the input greeting message to be formatted, may be null or empty
     * @return a formatted greeting string; either the input greeting if it has text or
     *         the default fallback "Dear Valued Customer"
     */
    private String formatGreeting(String greeting) {
        return StringUtils.hasText(greeting) ? greeting : "Dear Valued Customer";
    }

    /**
     * Formats the provided signature by appending the company name and a separator line.
     *
     * @param signature the base signature text to format
     * @return the formatted signature including the company name and separator line
     */
    private String formatSignature(String signature) {
        StringBuilder formattedSignature = new StringBuilder();
        formattedSignature.append(signature)
                .append("\n")
                .append(companyName)
                .append("\n")
                .append("-------------------");
        return formattedSignature.toString();
    }

}
