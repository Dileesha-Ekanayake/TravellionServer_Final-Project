package lk.travel.travellion.reports.paymentcollection;

import lk.travel.travellion.apiresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/payment-collection-reports")
@RequiredArgsConstructor
public class PaymentCollectionDataController {

    private final PaymentCollectionDataService paymentCollectionDataService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<PaymentCollectionDTO>>> getPaymentCollections(@RequestParam String startDate, @RequestParam String endDate) {
        List<PaymentCollectionDTO> paymentCollectionDTOs = paymentCollectionDataService.getPaymentCollectionByDateRange(startDate, endDate);
        paymentCollectionDataService.getPaymentCollectionByDateRange(startDate, endDate);
        return ApiResponse.successResponse(paymentCollectionDTOs);
    }
}
