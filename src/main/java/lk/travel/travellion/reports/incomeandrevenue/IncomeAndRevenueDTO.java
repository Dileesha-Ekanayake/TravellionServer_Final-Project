package lk.travel.travellion.reports.incomeandrevenue;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class IncomeAndRevenueDTO {

    private int year;
    private int month;
    private String monthName;
    private BigDecimal totalBookings;
    private BigDecimal totalRevenue;
    private BigDecimal accommodationRevenue;
    private BigDecimal transferRevenue;
    private BigDecimal genericRevenue;
    private BigDecimal tourRevenue;

}
