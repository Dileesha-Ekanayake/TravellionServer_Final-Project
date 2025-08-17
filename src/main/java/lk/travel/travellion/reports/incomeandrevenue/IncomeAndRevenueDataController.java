package lk.travel.travellion.reports.incomeandrevenue;

import lk.travel.travellion.apiresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/income-and-revenue-reports")
@RequiredArgsConstructor
public class IncomeAndRevenueDataController {

    private final IncomeAndRevenueDataService incomeAndRevenueDataService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<IncomeAndRevenueDTO>>> getIncomeAndRevenue(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String type) {
        List<IncomeAndRevenueDTO> incomeAndRevenueDTOs = incomeAndRevenueDataService.getIncomeAndRevenueByDateRange(startDate, endDate, type);
        return ApiResponse.successResponse(incomeAndRevenueDTOs);
    }
}
