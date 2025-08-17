package lk.travel.travellion.dto.supplierdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Supplierstatus;
import lk.travel.travellion.entity.Suppliertype;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link lk.travel.travellion.entity.Supplier}
 */
@Value
public class SupplierResponseDTO implements Serializable {
    Integer id;
    UserDTO user;
    @Size(max = 10)
    String brno;
    @Size(max = 255)
    String name;
    byte[] photo;
    @Size(max = 10)
    String mobile;
    @Size(max = 10)
    String land;
    @Size(max = 100)
    String email;
    @Size(max = 255)
    String address;
    @Size(max = 100)
    String city;
    @Size(max = 100)
    String state;
    @Size(max = 50)
    String country;
    @Size(max = 10)
    String zipcode;
    @Size(max = 45)
    String bankAccount;
    String description;
    Timestamp createdon;
    Timestamp updatedon;
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
