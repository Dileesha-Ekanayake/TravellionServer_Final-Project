package lk.travel.travellion.dto.supplierdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Supplierstatus}
 */
@Value
public class SupplierstatusDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
