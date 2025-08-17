package lk.travel.travellion.dto.employeedto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Employeestatus}
 */
@Value
public class EmployeestatusDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
