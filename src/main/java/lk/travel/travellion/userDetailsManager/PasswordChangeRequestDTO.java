package lk.travel.travellion.userDetailsManager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class PasswordChangeRequestDTO {

    private String userName;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
