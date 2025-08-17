package lk.travel.travellion.dto.accommodationdto;

import jakarta.validation.constraints.NotNull;
import lk.travel.travellion.entity.Accommodationdiscounttype;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link lk.travel.travellion.entity.Accommodationdiscount}
 */
@Value
public class AccommodationdiscountResponseDTO implements Serializable {
    Integer id;
    BigDecimal amount;
    @NotNull
    Accommodationdiscounttype accommodationdiscounttype;
}
