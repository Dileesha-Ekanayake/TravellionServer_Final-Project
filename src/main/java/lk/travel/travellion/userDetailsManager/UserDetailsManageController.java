package lk.travel.travellion.userDetailsManager;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.entity.User;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/user-details-manager")
@RequiredArgsConstructor
public class UserDetailsManageController {

    private final UserDetailManageHelper userDetailManageHelper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Handles updating the password for a user.
     *
     * @param passwordChangeRequestDTO Data transfer object containing the old password, new password,
     *                                  confirm password, and the username of the user requesting the update.
     * @return A ResponseEntity containing the ApiResponse with a success message and the updated username.
     * @throws ResourceNotFoundException If the user is not found, the passwords do not match,
     *                                    the old password is incorrect, or necessary fields are missing.
     * @throws ResourceAlreadyExistException If a user with the specified username already exists but has a different ID.
     */
    @PutMapping("/update-password")
    public ResponseEntity<ApiResponse<String>> passwordUpdate(@Valid @RequestBody PasswordChangeRequestDTO passwordChangeRequestDTO) {

        User existingUser = userRepository.findByUsername(passwordChangeRequestDTO.getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User with username : " + passwordChangeRequestDTO.getUserName() + " not found"));

        if (!existingUser.getUsername().equals(passwordChangeRequestDTO.getUserName()) &&
                userRepository.existsByUsernameAndIdNot(passwordChangeRequestDTO.getUserName(), existingUser.getId())) {
            throw new ResourceAlreadyExistException("User with name: " + passwordChangeRequestDTO.getUserName() + " already exists");
        }

        if (
                (passwordChangeRequestDTO.getNewPassword() != null && !passwordChangeRequestDTO.getNewPassword().isEmpty()) &&
                        (passwordChangeRequestDTO.getConfirmPassword() != null && !passwordChangeRequestDTO.getConfirmPassword().isEmpty()) &&
                        (passwordChangeRequestDTO.getOldPassword() != null && !passwordChangeRequestDTO.getOldPassword().isEmpty())
        ) {
            // Validate new password and confirm password match
            if (!passwordChangeRequestDTO.getNewPassword().equals(passwordChangeRequestDTO.getConfirmPassword())) {
                throw new ResourceNotFoundException("New password and confirm password do not match");
            }

            // Check if the new password is the same as the old password
            if (passwordEncoder.matches(passwordChangeRequestDTO.getNewPassword(), existingUser.getPassword())) {
                throw new ResourceNotFoundException("New password cannot be the same as the old password");
            }

            // Validate old password
            if (!passwordEncoder.matches(passwordChangeRequestDTO.getOldPassword(), existingUser.getPassword())) {
                throw new ResourceNotFoundException("Old password is incorrect");
            }

            // All validations passed; update password
            existingUser.setPassword(passwordEncoder.encode(passwordChangeRequestDTO.getNewPassword()));
            userRepository.save(existingUser);

            return ApiResponse.successResponse(
                    "Success",
                    HttpStatus.OK,
                    existingUser.getUsername()
            );
        } else {
            throw new ResourceNotFoundException("Old password, new password and confirm password are required");
        }
    }

    /**
     * Finds a user based on the provided username and retrieves the employee's email address.
     *
     * @param username the username of the user to be searched
     * @return a ResponseEntity containing an ApiResponse with the employee's email address if the user is found
     */
    @PostMapping("/forget-password")
    public ResponseEntity<ApiResponse<String>> findUserByUserName(@RequestBody final String username) {
        Optional<User> foundUser = Optional.ofNullable(userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));

        String employeeEmail = "";
        if (foundUser.isPresent()) {
            employeeEmail = foundUser.get().getEmployee().getEmail();
        }
        return ApiResponse.successResponse(
                "Success",
                HttpStatus.OK,
                employeeEmail
        );
    }

    /**
     * Sends a recovery code to the email associated with the provided username.
     * The recovery code is generated, sent to the user's email, and saved for verification.
     *
     * @param username the username of the user requesting the recovery code
     * @return ResponseEntity containing an ApiResponse with a success message and the user's email on success,
     *         or throws a ResourceNotFoundException if the user is not found
     */
    @PostMapping("/send-recovery-code")
    public ResponseEntity<ApiResponse<String>> sendRecoveryCode(@RequestBody final String username) {

        Optional<User> foundUser = userRepository.findByUsername(username);

        if (foundUser.isPresent()) {

            User user = foundUser.get();

            String recoverCode = userDetailManageHelper.generateRecoverCode();
            userDetailManageHelper.sendRecoveryCode(user.getEmployee().getEmail(), recoverCode);
            userDetailManageHelper.saveRecoveryCode(user, recoverCode);

            return ApiResponse.successResponse(
                    "Email sent successfully",
                    HttpStatus.OK,
                    user.getEmployee().getEmail()
            );
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    /**
     * Verifies the provided recovery code for a user's password reset operation.
     * Checks if the recovery code is valid, not expired, and has not been used.
     * If valid, updates the user's password and marks the recovery code as used.
     *
     * @param passwordResetRequest The request payload containing the user's username, recovery code, and new password.
     * @return A ResponseEntity containing an ApiResponse indicating success or error.
     *         On success, returns a message confirming the password update.
     *         On error, returns an error message and corresponding HTTP status. Possible errors include
     *         invalid or expired recovery code, or user not found.
     */
    @PostMapping("/verify-recovery-code")
    public ResponseEntity<ApiResponse<String>> verifyRecoveryCode(@RequestBody final PasswordResetRequest passwordResetRequest) {

        Optional<User> foundUser = userRepository.findByUsername(passwordResetRequest.getUserName());
        if (foundUser.isPresent()) {
            User user = foundUser.get();

            if (user.getRecoveryCode().equals(passwordResetRequest.getRecoveryCode()) &&
                    user.getRecoveryCodeExpiration().toLocalDateTime().isAfter(LocalDateTime.now()) &&
                    !user.getRecoveryCodeUsed()) {
                user.setRecoveryCodeUsed(true);
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                user.setPassword(passwordEncoder.encode( passwordResetRequest.getNewPassword()));

                userRepository.save(user);

                return ApiResponse.successResponse("Password updated successfully.");
            } else {
                return ApiResponse.errorResponse("Invalid or expired recovery code.", HttpStatus.NOT_FOUND, null);
            }
        } else {
            return ApiResponse.errorResponse("User not found.", HttpStatus.NOT_FOUND, null);
        }
    }
}
