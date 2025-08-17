package lk.travel.travellion.dto.setupdetailsdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Currency}
 */
@Value
public class CurrencyDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
