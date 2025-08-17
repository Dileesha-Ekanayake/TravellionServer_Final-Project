package lk.travel.travellion.dto.locationdto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Airport}
 */
@Value
@NoArgsConstructor(force = true)
public class AirportRequestDTO implements Serializable {
    Integer id;
    @Size(max = 6)
    @Pattern(regexp = "^[A-Z]{3}$", message = "Invalid Code")
    String code;
    @Size(max = 45)
    @Pattern(regexp = "^([A-Z][a-z]*[.]?[\\s]?)*([A-Z][a-z]*)$", message = "Invalid Name")
    String name;
    byte[] photo;

}
