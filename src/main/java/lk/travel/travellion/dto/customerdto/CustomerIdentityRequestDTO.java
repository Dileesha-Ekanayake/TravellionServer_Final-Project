package lk.travel.travellion.dto.customerdto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link lk.travel.travellion.entity.CustomerIdentity}
 */
@Value
@NoArgsConstructor(force = true)
public class CustomerIdentityRequestDTO implements Serializable {
    Integer id;
    @Size(max = 15)
    @Pattern(regexp = "^(([\\d]{9}[vVxX])|([\\d]{12}))$", message = "Invalid NIC")
    String nic;
    @Size(max = 9)
    @Pattern(regexp = "^[A-Z0-9]{6,9}$", message = "Invalid Passport Number")
    String passportNo;
    LocalDate passportExpiryDate;
}
