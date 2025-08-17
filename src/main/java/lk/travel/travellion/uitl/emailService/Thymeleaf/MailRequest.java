package lk.travel.travellion.uitl.emailService.Thymeleaf;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailRequest {

    private String to;
    private String subject;
    private String recipientName;
    private String content;
    private String signature;
    private String templateName;
}
