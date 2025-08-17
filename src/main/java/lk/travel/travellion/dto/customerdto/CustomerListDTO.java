package lk.travel.travellion.dto.customerdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Customer}
 */
@Value
public class CustomerListDTO implements Serializable {
    Integer id;
    @Size(max = 11)
    String code;
    @Size(max = 100)
    String fullname;
}
