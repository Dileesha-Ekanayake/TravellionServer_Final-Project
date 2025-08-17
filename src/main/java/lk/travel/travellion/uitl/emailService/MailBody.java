package lk.travel.travellion.uitl.emailService;


import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Represents the body of an email, including recipient information, subject,
 * optional greeting, main content, closing remarks, and signature. This class
 * provides a flexible way to compose emails with customizable content and default
 * values for greetings, closing, and signature.
 *
 * Key Features:
 * - Holds the recipient's email address and the subject of the email.
 * - Allows setting a custom greeting, email content, closing remarks, and signature.
 * - Provides default values for greeting, closing, and signature fields if not explicitly set.
 * - Includes a validation method to ensure essential fields (to, subject, content) are non-empty.
 *
 * This class is typically used in conjunction with email services to format
 * and send emails.
 */
@Data
@Builder
public class MailBody {

    private String to;
    private String subject;

    @Builder.Default
    private String greetings = "Dear Valued Customer";

    private String content;

    @Builder.Default
    private String closing = "Thank you for your attention";

    @Builder.Default
    private String signature = "Best regards,\nAdmin";

    /**
     * Validates the essential fields of the email data. This method ensures that the
     * recipient's email address, the subject of the email, and the email's main content
     * are provided and non-empty. If any of these fields are missing or contain only
     * whitespace, the method will throw an IllegalArgumentException.
     *
     * Validation Criteria:
     * - The recipient's email address (to) must be non-null and contain non-whitespace characters.
     * - The email subject (subject) must be non-null and contain non-whitespace characters.
     * - The email content (content) must be non-null and contain non-whitespace characters.
     *
     * @throws IllegalArgumentException if any of the required fields (to, subject, content) are
     *                                  empty or contain only whitespace.
     */
    public void validate() {
        if (!StringUtils.hasText(to)) {
            throw new IllegalArgumentException("Email recipient cannot be empty");
        }
        if (!StringUtils.hasText(subject)) {
            throw new IllegalArgumentException("Email subject cannot be empty");
        }
        if (!StringUtils.hasText(content)) {
            throw new IllegalArgumentException("Email content cannot be empty");
        }
    }

}
