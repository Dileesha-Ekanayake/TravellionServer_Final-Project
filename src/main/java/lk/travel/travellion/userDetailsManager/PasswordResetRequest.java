package lk.travel.travellion.userDetailsManager;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordResetRequest {

    private String userName;
    private String recoveryCode;
    private String newPassword;

}
