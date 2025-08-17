package lk.travel.travellion.reports.profitabilityanalysisbytourtype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TotalTourBookingsAndRevenueDTO {

    private int totalBookings;
    private BigDecimal totalPaid;
    private BigDecimal supplierAmount;
}
