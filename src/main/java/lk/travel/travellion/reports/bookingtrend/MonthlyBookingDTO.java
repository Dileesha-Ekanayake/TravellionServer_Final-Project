package lk.travel.travellion.reports.bookingtrend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyBookingDTO {

    String month;
    Long bookingCount;
}
