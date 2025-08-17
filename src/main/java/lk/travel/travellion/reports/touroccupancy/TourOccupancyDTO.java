package lk.travel.travellion.reports.touroccupancy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TourOccupancyDTO {

    int year;
    int month;
    String monthName;
    String tourName;
    long tourCount;
    long totalBookingPassengerCount;
    int maxpaxcount;
    BigDecimal utilizationRate;

    public TourOccupancyDTO(int year, int month, String monthName, String tourName, long tourCount, long totalBookingPassengerCount, int maxpaxcount) {
        this.year = year;
        this.month = month;
        this.monthName = monthName;
        this.tourName = tourName;
        this.tourCount = tourCount;
        this.totalBookingPassengerCount = totalBookingPassengerCount;
        this.maxpaxcount = maxpaxcount;
    }
}
