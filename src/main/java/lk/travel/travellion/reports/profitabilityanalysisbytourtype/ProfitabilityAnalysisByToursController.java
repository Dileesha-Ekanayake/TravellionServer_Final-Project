package lk.travel.travellion.reports.profitabilityanalysisbytourtype;

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
@RequestMapping("/profits-by-tour")
@RequiredArgsConstructor
public class ProfitabilityAnalysisByToursController {

    private final ProfitabilityAnalysisByToursService profitabilityAnalysisByToursService;

    @GetMapping(path = "/tour-revenue", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<TotalTourBookingsAndRevenueDTO>> getTotalTourBookingsAndRevenue() {
        TotalTourBookingsAndRevenueDTO totalTourBookingsAndRevenue = profitabilityAnalysisByToursService.getTotalTourBookingsAndRevenue();
        return ApiResponse.successResponse(totalTourBookingsAndRevenue);
    }

    @GetMapping(path = "/types", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ProfitabilityAnalysisByTourTypeDTO>>> getProfitabilityAnalysisByTourType() {
        List<ProfitabilityAnalysisByTourTypeDTO> profitabilityAnalysisByTourType = profitabilityAnalysisByToursService.getProfitabilityAnalysisByTourType();
        return ApiResponse.successResponse(profitabilityAnalysisByTourType);
    }

    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ProfitabilityAnalysisByTourCategoryDTO>>> getProfitabilityAnalysisByTourCategory() {
        List<ProfitabilityAnalysisByTourCategoryDTO> profitabilityAnalysisByTourCategory = profitabilityAnalysisByToursService.getProfitabilityAnalysisByTourCategory();
        return ApiResponse.successResponse(profitabilityAnalysisByTourCategory);
    }

    @GetMapping(path = "/theme", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ProfitabilityAnalysisByTourThemeDTO>>> getProfitabilityAnalysisByTourTheme() {
        List<ProfitabilityAnalysisByTourThemeDTO> profitabilityAnalysisByTourTheme = profitabilityAnalysisByToursService.getProfitabilityAnalysisByTourTheme();
        return ApiResponse.successResponse(profitabilityAnalysisByTourTheme);
    }


}
