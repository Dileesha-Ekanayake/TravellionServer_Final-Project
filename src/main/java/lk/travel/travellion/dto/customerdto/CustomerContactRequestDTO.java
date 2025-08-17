package lk.travel.travellion.dto.customerdto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.CustomerContact}
 */
@Value
@NoArgsConstructor(force = true)
public class CustomerContactRequestDTO implements Serializable {
    Integer id;
    @Size(max = 10)
    @Pattern(regexp = "^((\\+94|0)(70|71|72|74|75|76|77|78)\\d{7})$", message = "Invalid Mobile Number")
    String mobile;
    @Size(max = 10)
    @Pattern(regexp = "^0[1-9]{1}[0-9]{1}-?[0-9]{6,7}$", message = "Invalid Land-phone Number")
    String land;
    @Size(max = 10)
    @Pattern(regexp = "^((\\+94|0)(70|71|72|74|75|76|77|78)\\d{7})$", message = "Invalid Mobile Number")
    String emergencyContactNumber;
    @Size(max = 100)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid Email")
    String email;
    @Size(max = 100)
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    String addressLine1;
    @Size(max = 100)
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    String addressLine2;
    @Size(max = 45)
    String postalCode;
    @Size(max = 45)
    String country;
    @Size(max = 45)
    String city;
}
