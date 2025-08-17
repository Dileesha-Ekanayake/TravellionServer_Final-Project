package lk.travel.travellion.dto.locationdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Location}
 */
@Value
public class LocationListDTO implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 45)
    String name;
}
