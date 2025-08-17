package lk.travel.travellion.dto.tourcontractdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Tourtype}
 */
@Value
public class TourtypeDTO implements Serializable {
    Integer id;
    @Size(max = 100)
    String name;
}
