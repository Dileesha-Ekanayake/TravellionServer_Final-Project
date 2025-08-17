package lk.travel.travellion.reports.monthlytoursummary;

import lk.travel.travellion.apiresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tour-reports")
@RequiredArgsConstructor
public class TourSummaryDataController {

    private final TourSummaryDataService tourSummaryDataService;

    @GetMapping(path = "/total-tours", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<TotalTourDataDTO>> getTotalTours() {
        TotalTourDataDTO monthlyTourDTOs = tourSummaryDataService.getTotalTourData();
        return ApiResponse.successResponse(monthlyTourDTOs);
    }

    @GetMapping(path = "/monthly-tours", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<MonthlyTourDTO>>> getMonthlyTours(@RequestParam String startDate, @RequestParam String endDate) {
        List<MonthlyTourDTO> monthlyTourDTOs = tourSummaryDataService.getMonthlyToursByDateRange(startDate, endDate);
        return ApiResponse.successResponse(monthlyTourDTOs);
    }
}
