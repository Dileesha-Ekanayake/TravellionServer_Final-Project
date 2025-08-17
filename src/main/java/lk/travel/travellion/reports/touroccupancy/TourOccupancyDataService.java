package lk.travel.travellion.reports.touroccupancy;

import lk.travel.travellion.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourOccupancyDataService {

    private final BookingRepository bookingRepository;

    /**
     * Retrieves a list of tour occupancy data within a specified date range.
     * The method calculates the utilization rate for each tour occupancy
     * entry based on the total booking passenger count and maximum capacity.
     *
     * @param startDate the start date of the range for which the tour occupancy data is retrieved
     * @param endDate the end date of the range for which the tour occupancy data is retrieved
     * @return a list of {@code TourOccupancyDTO} objects containing
     *         the occupancy data for the specified date range
     */
    public List<TourOccupancyDTO> getTourOccupancyByDateRange(LocalDate startDate, LocalDate endDate) {
        List<TourOccupancyDTO> tourOccupancyDataList = new ArrayList<>();

        LocalDate currentMonth = startDate.withDayOfMonth(1);
        LocalDate rangeEnd = endDate.withDayOfMonth(endDate.lengthOfMonth());

        while (!currentMonth.isAfter(rangeEnd)) {
            LocalDate monthStart = currentMonth.withDayOfMonth(1);
            LocalDate monthEnd = currentMonth.withDayOfMonth(currentMonth.lengthOfMonth());

            // Restrict the range within start and end dates
            LocalDate actualStart = monthStart.isBefore(startDate) ? startDate : monthStart;
            LocalDate actualEnd = monthEnd.isAfter(endDate) ? endDate : monthEnd;

            Timestamp startTimestamp = Timestamp.valueOf(actualStart.atStartOfDay());
            Timestamp endTimestamp = Timestamp.valueOf(actualEnd.atTime(23, 59, 59));

            List<TourOccupancyDTO> result = bookingRepository.getTourOccupancyBuTourTypes(actualStart, actualEnd);

            for (TourOccupancyDTO dto : result) {
                // If you need to recalculate or apply your safeValue method
                BigDecimal recalculatedRate = calculateUtilizationRate(
                        safeValue(BigDecimal.valueOf(dto.getTotalBookingPassengerCount())),
                        safeValue(dto.getMaxpaxcount())
                );
                dto.setUtilizationRate(recalculatedRate);
            }

            tourOccupancyDataList.addAll(result);
            currentMonth = currentMonth.plusMonths(1);
        }

        return tourOccupancyDataList;
    }

    /**
     * Retrieves a list of tour occupancy details within the specified date range.
     * The input date range is provided as strings that are internally converted to {@code LocalDate}.
     *
     * @param startDateStr the start date of the range in string format, expected in ISO-8601 format (e.g., "yyyy-MM-dd")
     * @param endDateStr   the end date of the range in string format, expected in ISO-8601 format (e.g., "yyyy-MM-dd")
     * @return a list of {@code TourOccupancyDTO} objects containing tour occupancy details for the specified date range
     */
    public List<TourOccupancyDTO> getTourOccupancyByDateRange(String startDateStr, String endDateStr) {
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return getTourOccupancyByDateRange(startDate, endDate);
    }

    /**
     * Safely converts the given object to a {@link BigDecimal}. If the input is null,
     * it returns a {@code BigDecimal.ZERO}.
     *
     * @param value the object to be converted to a {@code BigDecimal}, can be null
     * @return the {@code BigDecimal} representation of the input object, or {@code BigDecimal.ZERO} if the input is null
     */
    private BigDecimal safeValue(Object value) {
        return value != null ? new BigDecimal(value.toString()) : BigDecimal.ZERO;
    }

    /**
     * Calculates the utilization rate as a percentage based on the total booking passenger count
     * and the maximum capacity.
     *
     * @param totalBookingPassengerCount the total number of passengers booked
     * @param maxCapacity the maximum capacity available
     * @return the utilization rate as a percentage, rounded to two decimal places. Returns 0 if the maximum capacity is zero.
     */
    private BigDecimal calculateUtilizationRate(BigDecimal totalBookingPassengerCount, BigDecimal maxCapacity) {
        BigDecimal totalCapacity = maxCapacity;
        if (totalCapacity.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return totalBookingPassengerCount
                .divide(totalCapacity, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100"))
                .setScale(2, RoundingMode.HALF_UP);
    }
}

