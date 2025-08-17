package lk.travel.travellion.dto.bookingdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Bookingitemstatus}
 */
@Value
public class BookingitemstatusDTO implements Serializable {
    Integer id;
    @Size(max = 45)
    String name;
}
