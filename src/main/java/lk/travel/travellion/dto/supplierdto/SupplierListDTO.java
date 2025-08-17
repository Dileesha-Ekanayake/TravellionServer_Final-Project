package lk.travel.travellion.dto.supplierdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Supplier}
 */
@Value
public class SupplierListDTO implements Serializable {
    Integer id;
    @Size(max = 10)
    String brno;
    @Size(max = 255)
    String name;
}
