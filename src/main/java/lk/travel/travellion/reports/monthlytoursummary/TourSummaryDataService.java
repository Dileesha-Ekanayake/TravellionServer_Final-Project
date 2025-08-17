package lk.travel.travellion.reports.monthlytoursummary;

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
public class TourSummaryDataService {

    private final BookingRepository bookingRepository;

    /**
     * Retrieves the total tour data including the count of all tours and
     * the count of pending tours.
     *
     * @return an instance of TotalTourDataDTO containing the total number of tours
     *         and the number of pending tours.
     */
    public TotalTourDataDTO getTotalTourData() {
        BigDecimal totalTourCount = BigDecimal.valueOf(bookingRepository.getAllBookingsTourCount());
        BigDecimal pendingTourCount = BigDecimal.valueOf(bookingRepository.getAllPendingBookingsTourCount());

        return new TotalTourDataDTO(
                totalTourCount,
                pendingTourCount
        );
    }

    /**
     * Retrieves a list of monthly tour data within a specified date range.
     * The method calculates the total number of tours and the number of pending tours for each month
     * within the given date range.
     *
     * @param startDate the start date of the range (inclusive)
     * @param endDate the end date of the range (inclusive)
     * @return a list of {@code MonthlyTourDTO} objects containing monthly tour statistics
     */
    public List<MonthlyTourDTO> getMonthlyToursByDateRange(LocalDate startDate, LocalDate endDate) {
        List<MonthlyTourDTO> monthlyTours = new ArrayList<>();

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

            Long bookingCount = bookingRepository.getAllBookingsTourCountByDateRange(startTimestamp, endTimestamp);
            Long pendingBookingTourCount = bookingRepository.getAllPendingBookingsTourCountByDateRange(startTimestamp, endTimestamp);

            MonthlyTourDTO monthlyData = new MonthlyTourDTO(
                    monthStart.format(DateTimeFormatter.ofPattern("yyyy-MM")),
                    bookingCount,
                    pendingBookingTourCount
            );

            monthlyTours.add(monthlyData);
            currentMonth = currentMonth.plusMonths(1);
        }

        return monthlyTours;
    }

    /**
     * Retrieves a list of monthly tour summaries within the specified date range.
     *
     * @param startDateStr the start date of the range in string format (e.g., "yyyy-MM-dd")
     * @param endDateStr the end date of the range in string format (e.g., "yyyy-MM-dd")
     * @return a list of {@link MonthlyTourDTO} objects containing tour summary data for each month in the specified range
     */
    public List<MonthlyTourDTO> getMonthlyToursByDateRange(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return getMonthlyToursByDateRange(startDate, endDate);
    }
}
