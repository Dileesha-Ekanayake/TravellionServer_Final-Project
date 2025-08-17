package lk.travel.travellion.dto.supplierdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Suppliertype}
 */
@Value
public class SuppliertypeDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
