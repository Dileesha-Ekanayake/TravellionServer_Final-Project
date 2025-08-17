package lk.travel.travellion.dto.genericdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Generictype}
 */
@Value
public class GenerictypeDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
