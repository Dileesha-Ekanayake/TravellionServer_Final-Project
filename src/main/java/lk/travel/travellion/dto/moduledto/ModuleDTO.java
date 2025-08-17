package lk.travel.travellion.dto.moduledto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Module}
 */
@Value
public class ModuleDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
