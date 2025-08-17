package lk.travel.travellion.dto.operationdto;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.Module;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Operation}
 */
@Value
@Getter
@Setter
@AllArgsConstructor
public class OperationDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String displayName;
    @Size(max = 45)
    String operation;
    Module module;
}
