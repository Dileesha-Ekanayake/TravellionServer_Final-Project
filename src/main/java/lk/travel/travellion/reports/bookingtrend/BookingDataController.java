package lk.travel.travellion.reports.bookingtrend;

import lk.travel.travellion.apiresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/booking-reports")
@RequiredArgsConstructor
public class BookingDataController {

    private final BookingDataService bookingDataService;

    @GetMapping(path = "/monthly-revenue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<MonthlyRevenueDTO>>> getMonthlyRevenue(@RequestParam String startDate, @RequestParam String endDate) {
        List<MonthlyRevenueDTO> monthlyRevenueDTOs = bookingDataService.getMonthlyRevenueByDateRange(startDate, endDate);
        return ApiResponse.successResponse(monthlyRevenueDTOs);
    }

    @GetMapping(path = "/monthly-bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<MonthlyBookingDTO>>> getMonthlyBookings(@RequestParam String startDate, @RequestParam String endDate) {
        List<MonthlyBookingDTO> monthlyRevenueDTOs = bookingDataService.getMonthlyBookingsByDateRange(startDate, endDate);
        bookingDataService.getBookingTrendByDateRange(startDate, endDate);
        return ApiResponse.successResponse(monthlyRevenueDTOs);
    }

    @GetMapping(path = "/monthly-bookings-trend", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<BookingTrendDTO>>> getMonthlyBookingsTrend(@RequestParam String startDate, @RequestParam String endDate) {
        List<BookingTrendDTO> monthlyBookingTrendDTOs =  bookingDataService.getBookingTrendByDateRange(startDate, endDate);
        return ApiResponse.successResponse(monthlyBookingTrendDTOs);
    }
}
