package lk.travel.travellion.dto.bookingdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link lk.travel.travellion.entity.Booking}
 */
@Value
public class BookingBalanceDTO implements Serializable {
    Integer id;
    @Size(max = 24)
    String code;
    BigDecimal netamount;
    BigDecimal totalpaid;
    BigDecimal balance;
}
