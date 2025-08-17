package lk.travel.travellion.reports.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RecentBookingDTO {

    private String bookingCode;
    private String customerName;
    private Object bookingDate;
    private String bookingStatus;
}
