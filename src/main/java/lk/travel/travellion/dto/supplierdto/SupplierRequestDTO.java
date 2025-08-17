package lk.travel.travellion.dto.supplierdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Supplierstatus;
import lk.travel.travellion.entity.Suppliertype;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Supplier}
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Value
public class SupplierRequestDTO implements Serializable {
    Integer id;
    UserDTO user;
    @Size(max = 10)
    @Pattern(regexp = "^BR\\d{8}$", message = "Invalid Number")
    String brno;
    @Size(max = 255)
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Name")
    String name;
    byte[] photo;
    @Size(max = 10)
    @Pattern(regexp = "^((\\+94|0)(70|71|72|74|75|76|77|78)\\d{7})$", message = "Invalid Mobile Number")
    String mobile;
    @Size(max = 10)
    @Pattern(regexp = "^0[1-9]{1}[0-9]{1}-?[0-9]{6,7}$", message = "Invalid Land-phone Number")
    String land;
    @Size(max = 100)
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid Email")
    String email;
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Address")
    @Size(max = 255)
    String address;
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid City")
    @Size(max = 100)
    String city;
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid State")
    @Size(max = 100)
    String state;
    @Pattern(regexp = "^([\\w\\/\\-,\\s]{2,})$", message = "Invalid Country")
    @Size(max = 50)
    String country;
    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "Invalid Zip-Code")
    @Size(max = 10)
    String zipcode;
    @Pattern(regexp = "^(\\d{3,12})$", message = "Invalid Account Number")
    @Size(max = 45)
    String bankAccount;
    String description;
    @NotNull
    Suppliertype suppliertype;
    @NotNull
    Supplierstatus supplierstatus;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDTO implements Serializable {
        Integer id;
        String username;
    }
}

