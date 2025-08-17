package lk.travel.travellion.reports.dashboard;

import lk.travel.travellion.repository.BookingRepository;
import lk.travel.travellion.repository.CustomerRepository;
import lk.travel.travellion.repository.CustomerpaymentRepository;
import lk.travel.travellion.repository.TourcontractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final BookingRepository bookingRepository;
    private final TourcontractRepository tourcontractRepository;
    private final CustomerRepository customerRepository;

    /**
     * Retrieves a list of recent booking data for dashboard display.
     * The recent bookings include details such as booking code, customer name,
     * booking date, and booking status. Only bookings created within the last
     * three days are included in the result.
     *
     * @return a list of {@code RecentBookingDTO} containing details of recent bookings
     */
    public List<RecentBookingDTO> getRecentBookingData() {
        return bookingRepository.getRecentBookings();
    }

    /**
     * Generates and returns the dashboard data compiled as a {@link DashboardCardDTO}, which includes information
     * such as booking counts, revenue, customer counts, payment statuses, and other key performance indicators.
     *
     * This method calculates various statistics:
     * - Booking data: Total bookings, booking percentage change compared to the previous month, and whether bookings are increasing.
     * - Revenue data: Total revenue, revenue percentage change compared to the previous month, and whether revenue is increasing.
     * - Tour data: Total tours, newly created tours, booking tour counts, and confirmed tour counts.
     * - Customer data: Total customers, customer percentage change compared to the previous month, and whether customer count is increasing.
     * - Payment data: Pending payments and overdue payments.
     *
     * @return an object of type {@link DashboardCardDTO} that encapsulates the dashboard data such as booking count,
     *         revenue statistics, customer metrics, payment statuses, and other performance-related indicators.
     */
    public DashboardCardDTO getDashboardCardData() {

        boolean isBookingIncreasing = false;
        boolean isRevenueIncreasing = false;
        boolean isCustomersIncreasing = false;


    //==================================================Booking Data=====================================================//
        BigDecimal totalBookingCount = BigDecimal.valueOf(bookingRepository.count());

        LocalDate now = LocalDate.now();

        // Current month
        LocalDate currentMonthStart = now.withDayOfMonth(1);
        LocalDate currentMonthEnd = now.withDayOfMonth(now.lengthOfMonth());

        // Last month
        LocalDate lastMonthStart = currentMonthStart.minusMonths(1);
        LocalDate lastMonthEnd = currentMonthStart.minusDays(1);

        // Convert to Timestamp
        Timestamp currentStart = Timestamp.valueOf(currentMonthStart.atStartOfDay());
        Timestamp currentEnd = Timestamp.valueOf(currentMonthEnd.atTime(23, 59, 59));

        Timestamp lastStart = Timestamp.valueOf(lastMonthStart.atStartOfDay());
        Timestamp lastEnd = Timestamp.valueOf(lastMonthEnd.atTime(23, 59, 59));

        long currentMonthCount = bookingRepository.countByCreatedonBetween(currentStart, currentEnd);
        long lastMonthCount = bookingRepository.countByCreatedonBetween(lastStart, lastEnd);

        BigDecimal bookingPercentageWithLastMonth = BigDecimal.ZERO;

        if (lastMonthCount != 0) {
            bookingPercentageWithLastMonth = BigDecimal.valueOf(currentMonthCount - lastMonthCount)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(lastMonthCount), 2, RoundingMode.HALF_UP);
        } else if (currentMonthCount > 0) {
            bookingPercentageWithLastMonth = BigDecimal.valueOf(100);
            isBookingIncreasing = true;
        }

//===================================================================================================================================//

//================================================Revenue Data============================================================================//

        // Current month totals
        BigDecimal totalAccommodationRevenue = bookingRepository.getTotalProfitAllBookingAccommodations();
        BigDecimal totalTransferRevenue = bookingRepository.getTotalProfitAllBookingTransferContracts();
        BigDecimal totalGenericRevenue = bookingRepository.getTotalProfitAllBookingGenerics();
        BigDecimal totalTourRevenue = bookingRepository.getTotalProfitAllBookingTours();

        // Last month totals
        BigDecimal lastMonthTotalAccommodationRevenue = bookingRepository.getTotalBookingAccommodationProfitByBookingDateRange(lastStart, lastEnd);
        BigDecimal lastMonthTotalTransferRevenue = bookingRepository.getTotalBookingTransferContractProfitByBookingDateRange(lastStart, lastEnd);
        BigDecimal lastMonthTotalGenericRevenue = bookingRepository.getTotalBookingGenericProfitByBookingDateRange(lastStart, lastEnd);
        BigDecimal lastMonthTotalTourRevenue = bookingRepository.getTotalBookingTourProfitByBookingDateRange(lastStart, lastEnd);

        // Calculate total revenues
        BigDecimal totalRevenue = totalAccommodationRevenue.add(totalTransferRevenue)
                .add(totalGenericRevenue)
                .add(totalTourRevenue);

        BigDecimal lastMonthTotalRevenue = lastMonthTotalAccommodationRevenue.add(lastMonthTotalTransferRevenue)
                .add(lastMonthTotalGenericRevenue)
                .add(lastMonthTotalTourRevenue);

        // Calculate percentage change
        BigDecimal revenuePercentageWithLastMonth = BigDecimal.ZERO;

        if (lastMonthTotalRevenue.compareTo(BigDecimal.ZERO) != 0) {
            revenuePercentageWithLastMonth = totalRevenue.subtract(lastMonthTotalRevenue)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(lastMonthTotalRevenue, 2, RoundingMode.HALF_UP);
        } else if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
            // If last month was 0 but the current month has revenue, it's a 100% increase
            revenuePercentageWithLastMonth = BigDecimal.valueOf(100);
            isRevenueIncreasing = true;
        }
//===================================================================================================================================//


//==================================================Get All Booking TOur Count===============================================================//
        BigDecimal bookingTourCount = BigDecimal.valueOf(bookingRepository.getAllBookingsTourCount());
        BigDecimal confirmedTourCount = BigDecimal.valueOf(bookingRepository.getAllConfirmedBookingsTourCount());

//===================================================All Tour Count==============================================================//

        BigDecimal totalTourCount = BigDecimal.valueOf(tourcontractRepository.count());
        BigDecimal newlyCreatedTourCount = BigDecimal.valueOf(tourcontractRepository.getAllNewlyCreatedTours());

//===================================================================================================================================//

//============================================================Customer Count=========================================================================//

        BigDecimal totalCustomerCount = BigDecimal.valueOf(customerRepository.count());

        long currentMonthCustomerCount = customerRepository.countByCreatedonBetween(currentStart, currentEnd);
        long lastMonthCustomerCount = customerRepository.countByCreatedonBetween(lastStart, lastEnd);

        BigDecimal customerPercentageWithLastMonth = BigDecimal.ZERO;

        if (lastMonthCustomerCount != 0) {
            customerPercentageWithLastMonth = BigDecimal.valueOf(currentMonthCustomerCount - lastMonthCustomerCount)
                    .multiply(BigDecimal.valueOf(100))
                    .divide(BigDecimal.valueOf(lastMonthCustomerCount), 2, RoundingMode.HALF_UP);
        } else if (currentMonthCustomerCount > 0) {
            customerPercentageWithLastMonth = BigDecimal.valueOf(100);
            isCustomersIncreasing = true;
        }

//===================================================================================================================================//

//==================================================Pending and Overdue payments============================================================//


        BigDecimal pendingPaymentsBookings = BigDecimal.valueOf(bookingRepository.getAllPendingPaymentsBookings());
        BigDecimal overduePaymentsBookings = BigDecimal.valueOf(bookingRepository.getAllOverduePaymentsBookings());


        return new DashboardCardDTO(
                totalBookingCount,
                bookingPercentageWithLastMonth,
                isBookingIncreasing,

                totalRevenue,
                revenuePercentageWithLastMonth,
                isRevenueIncreasing,

                bookingTourCount,
                confirmedTourCount,

                totalTourCount,
                newlyCreatedTourCount,

                totalCustomerCount,
                customerPercentageWithLastMonth,
                isCustomersIncreasing,

                pendingPaymentsBookings,
                overduePaymentsBookings
        );

    }
}
