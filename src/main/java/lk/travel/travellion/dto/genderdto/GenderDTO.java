package lk.travel.travellion.dto.genderdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Gender}
 */
@Value
public class GenderDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
