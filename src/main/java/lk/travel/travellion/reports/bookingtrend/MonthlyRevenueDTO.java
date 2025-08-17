package lk.travel.travellion.reports.bookingtrend;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MonthlyRevenueDTO {

    String month;
    BigDecimal accommodationRevenue;
    BigDecimal transferRevenue;
    BigDecimal genericRevenue;
    BigDecimal tourRevenue;
    BigDecimal totalRevenue;

}
