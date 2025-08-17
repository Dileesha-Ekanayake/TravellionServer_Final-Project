package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.supplierpaymentdto.PaymentstatusDTO;
import lk.travel.travellion.service.suplierpayment.SupplierPaymentStatusService;
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
@RequestMapping("/supplierpaymentstatuses")
@RequiredArgsConstructor
public class SupplierPaymentStatusController {

    private final SupplierPaymentStatusService supplierPaymentStatusService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<PaymentstatusDTO>>> getSupplierPaymentStatus() {
        return ApiResponse.successResponse(supplierPaymentStatusService.getAllSupplierPaymentStatus());
    }
}
