package lk.travel.travellion.reports.dashboard;

import lk.travel.travellion.apiresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping(path = "/cards", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<DashboardCardDTO>> getDashboardData() {
        DashboardCardDTO dashboardCardDTO = dashboardService.getDashboardCardData();
        return ApiResponse.successResponse(dashboardCardDTO);
    }

    @GetMapping(path = "/recent-bookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RecentBookingDTO>>> getRecentBookingData() {
        List<RecentBookingDTO> recentBookingDTO = dashboardService.getRecentBookingData();
        return ApiResponse.successResponse(recentBookingDTO);
    }

}
