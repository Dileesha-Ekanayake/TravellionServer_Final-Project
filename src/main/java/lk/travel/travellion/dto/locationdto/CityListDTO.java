package lk.travel.travellion.dto.locationdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.City}
 */
@Value
public class CityListDTO implements Serializable {
    Integer id;
    @NotNull
    @Size(max = 18)
    String code;
    @Size(max = 45)
    String name;
}
