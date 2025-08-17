package lk.travel.travellion.dto.bookingdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link lk.travel.travellion.entity.Booking}
 */
@Value
public class BookingListDTO implements Serializable {
    Integer id;
    @Size(max = 24)
    String code;
    BigDecimal grossamount;
    BigDecimal discountamount;
    BigDecimal netamount;
    BigDecimal totalpaid;
    BigDecimal balance;
    LocalDate departuredate;
    LocalDate enddate;
}
