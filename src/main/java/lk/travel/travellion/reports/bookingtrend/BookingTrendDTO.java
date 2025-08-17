package lk.travel.travellion.reports.bookingtrend;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BookingTrendDTO {

    private int year;
    private int month;
    private String monthName;
    private BigDecimal confirmCount;
    private BigDecimal partiallyConfirmCount;
    private BigDecimal pendingCount;
    private BigDecimal totalCount;
    private BigDecimal doneCount;
}
