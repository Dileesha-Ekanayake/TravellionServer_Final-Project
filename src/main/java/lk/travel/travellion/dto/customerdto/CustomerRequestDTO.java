package lk.travel.travellion.dto.customerdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.*;
import lk.travel.travellion.uitl.regexProvider.RegexPattern;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Customer}
 */
@Value
@NoArgsConstructor(force = true)
public class CustomerRequestDTO implements Serializable {
    Integer id;
    @Size(max = 11)
    @Pattern(regexp = "^[A-Z]{3}_[0-9]{2}_[0-9]{4}$", message = "Invalid Code")
    String code;
    @Size(max = 100)
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Fullname")
    String fullname;
    @Size(max = 45)
    @Pattern(regexp = "^([A-Z][a-z]+)$", message = "Invalid Callingname")
    String callingname;
    @RegexPattern(reg = "^(?:\\d{4}-\\d{2}-\\d{2}|\\d{2}/\\d{2}/\\d{4})$", msg = "Invalid Date Format")
    LocalDate dobirth;
    String description;
    @NotNull
    UserDTO user;
    @NotNull
    Residenttype residenttype;
    Set<CustomerContactRequestDTO> customerContacts;
    Set<CustomerIdentityRequestDTO> customerIdentities;
    Set<Passenger> passengers;
    @NotNull
    Gender gender;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDTO implements Serializable {
        Integer id;
        String username;
    }
}
