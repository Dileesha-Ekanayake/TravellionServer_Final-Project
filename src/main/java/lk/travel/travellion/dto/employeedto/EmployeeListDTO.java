package lk.travel.travellion.dto.employeedto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Employee}
 */
@Value
@Getter
@Setter
@AllArgsConstructor
public class EmployeeListDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String callingname;
}
