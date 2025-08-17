package lk.travel.travellion.dto.tourcontractdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Tourcategory}
 */
@Value
public class TourcategoryDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
