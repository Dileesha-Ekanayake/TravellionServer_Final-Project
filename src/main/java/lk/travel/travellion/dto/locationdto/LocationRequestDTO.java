package lk.travel.travellion.dto.locationdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.City;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.checkerframework.common.aliasing.qual.Unique;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Location}
 */
@Value
@NoArgsConstructor(force = true)
public class LocationRequestDTO implements Serializable {
    Integer id;
    UserDTO user;
    @NotNull
    @Size(max = 26)
    @Unique
    @Pattern(regexp = "^[A-Z]{3}_[A-Z]{3}_[A-Z]{2}-[0-9]+_[A-Z]{2}-[0-9]+_[0-9]{3}_[0-9]{3}$", message = "Invalid Code")
    String code;
    @Size(max = 45)
    @Pattern(regexp = "^([A-Za-z0-9\\'\\\"\\(\\)\\[\\]\\-,\\.\\&]+([\\s][A-Za-z0-9\\'\\\"\\(\\)\\[\\]\\-,\\.\\&]+)*)([\\s])?$", message = "Invalid Name")
    String name;
    byte[] photo;
    String description;
    City city;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDTO implements Serializable {
        Integer id;
        String username;
    }
}
