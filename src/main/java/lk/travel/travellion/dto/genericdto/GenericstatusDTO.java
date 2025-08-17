package lk.travel.travellion.dto.genericdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Genericstatus}
 */
@Value
public class GenericstatusDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
