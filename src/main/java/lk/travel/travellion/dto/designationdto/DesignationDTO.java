package lk.travel.travellion.dto.designationdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Designation}
 */
@Value
public class DesignationDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
