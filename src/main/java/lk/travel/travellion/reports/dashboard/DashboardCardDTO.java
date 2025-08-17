package lk.travel.travellion.reports.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DashboardCardDTO {

    BigDecimal totalBookings;
    BigDecimal bookingPercentageWithLastMonth;
    boolean isBookingIncreasing;

    BigDecimal totalRevenue;
    BigDecimal revenuePercentageWithLastMonth;
    boolean isRevenueIncreasing;

    BigDecimal totalTrips;
    BigDecimal confirmedTrips;

    BigDecimal activeTors;
    BigDecimal newlyAddedTours;

    BigDecimal newCustomers;
    BigDecimal customersPercentageWithLastMonthData;
    boolean isCustomersIncreasing;

    BigDecimal pendingPayments;
    BigDecimal overduePayments;

}
