package lk.travel.travellion.dto.employeedto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Employeetype}
 */
@Value
public class EmployeetypeDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
