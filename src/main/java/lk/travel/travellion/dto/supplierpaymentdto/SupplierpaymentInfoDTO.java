package lk.travel.travellion.dto.supplierpaymentdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Supplierpayment}
 */
@Value
public class SupplierpaymentInfoDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String code;
    BigDecimal totalamounttobepaid;
    BigDecimal previoustotalpaidamount;
    BigDecimal balance;
}
