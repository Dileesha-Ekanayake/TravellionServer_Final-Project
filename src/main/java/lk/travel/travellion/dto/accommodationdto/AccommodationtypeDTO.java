package lk.travel.travellion.dto.accommodationdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Accommodationtype}
 */
@Value
public class AccommodationtypeDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
