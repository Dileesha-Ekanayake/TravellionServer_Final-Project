package lk.travel.travellion.reports.touroccupancy;

import lk.travel.travellion.apiresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/tour-occupancy-reports")
@RequiredArgsConstructor
public class TourOccupancyDataController {

    private final TourOccupancyDataService tourOccupancyDataService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<TourOccupancyDTO>>> getTourOccupancy(@RequestParam String startDate, @RequestParam String endDate) {
        List<TourOccupancyDTO> paymentCollectionDTOs = tourOccupancyDataService.getTourOccupancyByDateRange(startDate, endDate);
        return ApiResponse.successResponse(paymentCollectionDTOs);
    }
}
