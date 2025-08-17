package lk.travel.travellion.dto.userdto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.User}
 */
@Value
public class UserActiveDeactiveDTO implements Serializable {
    @Size(max = 45)
    String username;
    @NotNull
    Boolean accountLocked;
}
