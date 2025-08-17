package lk.travel.travellion.reports.bookingtrend;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TotalBookingDTO {

    private BigDecimal totalBookings;
    private BigDecimal pendingBookings;
    private BigDecimal confirmedBookings;
}
