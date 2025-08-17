package lk.travel.travellion.dto.transfercontractdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Locationtype}
 */
@Value
public class LocationtypeDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
