package lk.travel.travellion.dto.locationdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.*;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.City}
 */
@Value
@NoArgsConstructor(force = true)
public class CityRequestDTO implements Serializable {
    Integer id;
    User user;
    @NotNull
    @Size(max = 18)
    @Pattern(regexp = "^[A-Z]{3}_[A-Z]{2}-[0-9]_[A-Z]{2}-[0-9]{2}_[0-9]{3}$", message = "Invalid Number")
    String code;
    @Size(max = 45)
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Name")
    String name;
    byte[] photo;
    @NotNull
    District district;
    Set<Airport> airports;
    Set<Location> locations;
    Set<Port> ports;
}
