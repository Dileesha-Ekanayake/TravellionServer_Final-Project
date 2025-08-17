package lk.travel.travellion.reports.incomeandrevenue;

import lk.travel.travellion.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class IncomeAndRevenueDataService {

    private final BookingRepository bookingRepository;

    /**
     * Retrieves the income and revenue data within a specified date range, grouped by month.
     * The data includes total bookings, total revenue, and revenue breakdowns for different booking types.
     *
     * @param startDate the start date of the date range
     * @param endDate the end date of the date range
     * @param type the specific type of booking for which revenue should be calculated
     *             (e.g., "Accommodation", "Transfer", "Generic", "Tour").
     *             If not specified or invalid, revenue for all types will be calculated.
     * @return a list of IncomeAndRevenueDTO objects containing income and revenue statistics
     *         for each month in the specified date range
     */
    public List<IncomeAndRevenueDTO> getIncomeAndRevenueByDateRange(LocalDate startDate, LocalDate endDate, String type) {
        List<IncomeAndRevenueDTO> monthlyDataList = new ArrayList<>();

        LocalDate currentMonth = startDate.withDayOfMonth(1);
        LocalDate rangeEnd = endDate.withDayOfMonth(endDate.lengthOfMonth());

        while (!currentMonth.isAfter(rangeEnd)) {
            LocalDate monthStart = currentMonth.withDayOfMonth(1);
            LocalDate monthEnd = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth());

            // Constrain to user-specified range
            LocalDate actualStart = monthStart.isBefore(startDate) ? startDate : monthStart;
            LocalDate actualEnd = monthEnd.isAfter(endDate) ? endDate : monthEnd;

            Timestamp startTimestamp = Timestamp.valueOf(actualStart.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(actualEnd.atTime(23, 59, 59));

            // Query data
            BigDecimal totalBookingCount = BigDecimal.valueOf(
                    bookingRepository.countByCreatedonBetween(startTimestamp, endTimestamp));

            BigDecimal accommodationRevenue = BigDecimal.ZERO;
            BigDecimal transferRevenue = BigDecimal.ZERO;
            BigDecimal genericRevenue = BigDecimal.ZERO;
            BigDecimal tourRevenue = BigDecimal.ZERO;
            switch (type) {
                case "Accommodation" -> accommodationRevenue = safeValue(
                        bookingRepository.getTotalBookingAccommodationProfitByBookingDateRange(startTimestamp, endTimestamp));
                case "Transfer" -> transferRevenue = safeValue(
                        bookingRepository.getTotalBookingTransferContractProfitByBookingDateRange(startTimestamp, endTimestamp));
                case "Generic" -> genericRevenue = safeValue(
                        bookingRepository.getTotalBookingGenericProfitByBookingDateRange(startTimestamp, endTimestamp));
                case "Tour" -> tourRevenue = safeValue(
                        bookingRepository.getTotalBookingTourProfitByBookingDateRange(startTimestamp, endTimestamp));
                default -> {
                    accommodationRevenue = safeValue(
                            bookingRepository.getTotalBookingAccommodationProfitByBookingDateRange(startTimestamp, endTimestamp));
                    transferRevenue = safeValue(
                            bookingRepository.getTotalBookingTransferContractProfitByBookingDateRange(startTimestamp, endTimestamp));
                    genericRevenue = safeValue(
                            bookingRepository.getTotalBookingGenericProfitByBookingDateRange(startTimestamp, endTimestamp));
                    tourRevenue = safeValue(
                            bookingRepository.getTotalBookingTourProfitByBookingDateRange(startTimestamp, endTimestamp));
                }
            }

            BigDecimal totalRevenue = accommodationRevenue
                    .add(transferRevenue)
                    .add(genericRevenue)
                    .add(tourRevenue);

            // Create DTO
            IncomeAndRevenueDTO dto = new IncomeAndRevenueDTO(
                    currentMonth.getYear(),
                    currentMonth.getMonthValue(),
                    currentMonth.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                    totalBookingCount,
                    totalRevenue,
                    accommodationRevenue,
                    transferRevenue,
                    genericRevenue,
                    tourRevenue
            );

            monthlyDataList.add(dto);
            currentMonth = currentMonth.plusMonths(1);
        }

        return monthlyDataList;
    }

    /**
     * Retrieves a list of income and revenue data for the specified date range and type.
     *
     * @param startDateStr the start date of the range in string format (e.g., "YYYY-MM-DD")
     * @param endDateStr the end date of the range in string format (e.g., "YYYY-MM-DD")
     * @param type the type of data to filter by (e.g., "Accommodation", "Transfer", "Generic", "Tour")
     * @return a list of IncomeAndRevenueDTO objects representing the income and revenue data for the specified date range
     */
    public List<IncomeAndRevenueDTO> getIncomeAndRevenueByDateRange(String startDateStr, String endDateStr, String type) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return getIncomeAndRevenueByDateRange(startDate, endDate, type);
    }

    /**
     * Returns a non-null BigDecimal value. If the input value is null, it returns BigDecimal.ZERO.
     *
     * @param value the BigDecimal value to be checked for null
     * @return the input value if not null, otherwise BigDecimal.ZERO
     */
    private BigDecimal safeValue(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}
