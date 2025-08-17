package lk.travel.travellion.userDetailsManager;

import lk.travel.travellion.entity.User;
import lk.travel.travellion.repository.UserRepository;
import lk.travel.travellion.uitl.emailService.EmailSenderService;
import lk.travel.travellion.uitl.emailService.MailBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserDetailManageHelper {

    private final EmailSenderService emailSenderService;
    private final UserRepository userRepository;

    /**
     * Generates a six-digit recovery code.
     *
     * @return A randomly generated six-digit recovery code as a String.
     */
    protected String generateRecoverCode(){
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    /**
     * Sends a recovery code to the specified email address for password reset purposes.
     * This method constructs an email containing the recovery code and sends it
     * using the configured email service.
     *
     * @param email the recipient's email address where the recovery code should be sent
     * @param code  the recovery code to be sent in the email
     */
    protected void sendRecoveryCode(String email, String code) {

        MailBody mailBody = MailBody.builder()
                .to(email)
                .subject("Password Reset Recovery Code")
                .content("Your recovery code is : " + code)
                .build();

        emailSenderService.sendMail(mailBody);
    }

    /**
     * Stores the recovery code and related details for the given user.
     *
     * @param user The {@link User} object representing the user for whom the recovery code is being saved.
     * @param recoveryCode A string containing the recovery code to be saved for the user.
     */
    protected void saveRecoveryCode(User user, String recoveryCode) {
        user.setRecoveryCode(recoveryCode);
        user.setRecoveryCodeExpiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)));
        user.setRecoveryCodeUsed(false);
        userRepository.save(user);
    }

}
