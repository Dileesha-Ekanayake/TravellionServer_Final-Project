package lk.travel.travellion.reports.bookingtrend;

import lk.travel.travellion.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingDataService {

    private final BookingRepository bookingRepository;

    /**
     * Retrieves the monthly revenue details for a given date range. The method calculates
     * revenue from various booking categories (accommodation, transfers, generic bookings,
     * and tours) for each month within the specified range.
     *
     * @param startDate the start date of the range for which monthly revenue needs to be calculated
     * @param endDate the end date of the range for which monthly revenue needs to be calculated
     * @return a list of {@code MonthlyRevenueDTO} objects containing revenue details for each month
     *         within the specified date range
     */
    public List<MonthlyRevenueDTO> getMonthlyRevenueByDateRange(LocalDate startDate, LocalDate endDate) {

        List<MonthlyRevenueDTO> monthlyRevenue = new ArrayList<>();

        // Start from the beginning of the start month
        LocalDate currentMonth = startDate.withDayOfMonth(1);
        // End at the end of the end month
        LocalDate rangeEnd = endDate.withDayOfMonth(endDate.lengthOfMonth());

        while (!currentMonth.isAfter(rangeEnd)) {
            LocalDate monthStart = currentMonth.withDayOfMonth(1);
            LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

            // Adjust the actual query range to not exceed the user's date range
            LocalDate actualStart = monthStart.isBefore(startDate) ? startDate : monthStart;
            LocalDate actualEnd = monthEnd.isAfter(endDate) ? endDate : monthEnd;

            Timestamp startTimestamp = Timestamp.valueOf(actualStart.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(actualEnd.atTime(23, 59, 59));

            BigDecimal accommodationRevenue = bookingRepository.getTotalBookingAccommodationProfitByBookingDateRange(startTimestamp, endTimestamp);
            BigDecimal transferRevenue = bookingRepository.getTotalBookingTransferContractProfitByBookingDateRange(startTimestamp, endTimestamp);
            BigDecimal genericRevenue = bookingRepository.getTotalBookingGenericProfitByBookingDateRange(startTimestamp, endTimestamp);
            BigDecimal tourRevenue = bookingRepository.getTotalBookingTourProfitByBookingDateRange(startTimestamp, endTimestamp);

            BigDecimal totalRevenue = accommodationRevenue.add(transferRevenue)
                    .add(genericRevenue)
                    .add(tourRevenue);

            MonthlyRevenueDTO monthlyData = new MonthlyRevenueDTO(
                    monthStart.format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    accommodationRevenue,
                    transferRevenue,
                    genericRevenue,
                    tourRevenue,
                    totalRevenue
            );

            monthlyRevenue.add(monthlyData);
            currentMonth = currentMonth.plusMonths(1);
        }

        return monthlyRevenue;
    }

    /**
     * Retrieves a list of monthly booking statistics within the specified date range.
     * Each entry in the list represents a month and the corresponding booking count.
     *
     * @param startDate the start date of the date range to calculate bookings, inclusive
     * @param endDate the end date of the date range to calculate bookings, inclusive
     * @return a list of MonthlyBookingDTO objects, each containing the month and the count of bookings for that month
     */
    public List<MonthlyBookingDTO> getMonthlyBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<MonthlyBookingDTO> monthlyBookings = new ArrayList<>();

        // Start from the beginning of the start month
        LocalDate currentMonth = startDate.withDayOfMonth(1);
        // End at the end of the end month
        LocalDate rangeEnd = endDate.withDayOfMonth(endDate.lengthOfMonth());

        while (!currentMonth.isAfter(rangeEnd)) {
            LocalDate monthStart = currentMonth.withDayOfMonth(1);
            LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

            // Adjust the actual query range to not exceed the user's date range
            LocalDate actualStart = monthStart.isBefore(startDate) ? startDate : monthStart;
            LocalDate actualEnd = monthEnd.isAfter(endDate) ? endDate : monthEnd;

            Timestamp startTimestamp = Timestamp.valueOf(actualStart.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(actualEnd.atTime(23, 59, 59));

            Long bookingCount = bookingRepository.countByCreatedonBetween(startTimestamp, endTimestamp);

            MonthlyBookingDTO monthlyData = new MonthlyBookingDTO(
                    monthStart.format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    bookingCount
            );

            monthlyBookings.add(monthlyData);
            currentMonth = currentMonth.plusMonths(1);
        }

        return monthlyBookings;
    }

    /**
     * Retrieves the booking trend for a specified date range, grouping data monthly and
     * returning statistics such as confirmed, partially confirmed, pending, total, and completed bookings.
     *
     * @param startDate the start date of the range to retrieve booking trends, inclusive.
     * @param endDate the end date of the range to retrieve booking trends, inclusive.
     * @return a list of BookingTrendDTO objects containing booking statistics for each month
     *         within the specified date range.
     */
    public List<BookingTrendDTO> getBookingTrendByDateRange(LocalDate startDate, LocalDate endDate) {
        List<BookingTrendDTO> bookingTrendDTOS = new ArrayList<>();

        LocalDate currentMonth = startDate.withDayOfMonth(1);
        LocalDate rangeEnd = endDate.withDayOfMonth(endDate.lengthOfMonth());

        while (!currentMonth.isAfter(rangeEnd)) {
            LocalDate actualStart = currentMonth;
            LocalDate actualEnd = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth());

            // Adjust based on user range
            if (actualStart.isBefore(startDate)) actualStart = startDate;
            if (actualEnd.isAfter(endDate)) actualEnd = endDate;

            List<BookingTrendDTO> result = bookingRepository.getMonthlyBookingCount(actualStart, actualEnd);
            bookingTrendDTOS.addAll(result);

            currentMonth = currentMonth.plusMonths(1);
        }

        return bookingTrendDTOS;
    }

    /**
     * Retrieves the monthly revenue data for a specified date range.
     *
     * @param startDateStr the start date of the range in string format (yyyy-MM-dd)
     * @param endDateStr the end date of the range in string format (yyyy-MM-dd)
     * @return a list of MonthlyRevenueDTO objects containing monthly revenue details
     */
    public List<MonthlyRevenueDTO> getMonthlyRevenueByDateRange(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return getMonthlyRevenueByDateRange(startDate, endDate);
    }

    /**
     * Retrieves a list of monthly booking statistics within the specified date range.
     * Each entry in the list represents a month and the corresponding booking count.
     *
     * @param startDateStr the start date of the date range to calculate bookings, in string format (yyyy-MM-dd)
     * @param endDateStr the end date of the date range to calculate bookings, in string format (yyyy-MM-dd)
     * @return a list of MonthlyBookingDTO objects, each containing the month and the count of bookings for that month
     */
    public List<MonthlyBookingDTO> getMonthlyBookingsByDateRange(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return getMonthlyBookingsByDateRange(startDate, endDate);
    }

    /**
     * Retrieves the booking trend for a specified date range, converting the date range
     * from string format to LocalDate before fetching the data. The method provides
     * statistics such as confirmed, partially confirmed, pending, total, and completed
     * bookings for the specified range.
     *
     * @param startDateStr the start date of the range in string format (yyyy-MM-dd)
     * @param endDateStr the end date of the range in string format (yyyy-MM-dd)
     * @return a list of BookingTrendDTO objects containing booking statistics for the specified date range
     */
    public List<BookingTrendDTO> getBookingTrendByDateRange(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return getBookingTrendByDateRange(startDate, endDate);
    }
}
