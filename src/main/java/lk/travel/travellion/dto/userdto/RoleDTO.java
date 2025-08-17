package lk.travel.travellion.dto.userdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Role}
 */
@Value
public class RoleDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
