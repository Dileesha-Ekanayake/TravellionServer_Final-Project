package lk.travel.travellion.dto.supplierpaymentdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Paymentstatus}
 */
@Value
public class PaymentstatusDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
