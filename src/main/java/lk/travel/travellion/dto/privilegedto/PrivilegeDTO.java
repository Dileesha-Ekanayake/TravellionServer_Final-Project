package lk.travel.travellion.dto.privilegedto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Module;
import lk.travel.travellion.entity.Operation;
import lk.travel.travellion.entity.Role;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Privilege}
 */
@Value
public class PrivilegeDTO implements Serializable {
    Integer id;
    @NotNull
    Role role;
    @NotNull
    Module module;
    Operation operation;
    @Size(max = 255)
    String authority;
}
