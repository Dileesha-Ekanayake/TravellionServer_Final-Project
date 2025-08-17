package lk.travel.travellion.dto.customerpaymentdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Paymenttype}
 */
@Value
public class PaymenttypeDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
