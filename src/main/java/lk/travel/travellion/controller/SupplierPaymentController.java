package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentInfoDTO;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentRequestDTO;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentResponseDTO;
import lk.travel.travellion.entity.Supplierpayment;
import lk.travel.travellion.service.suplierpayment.SupplierPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/supplierpayments")
@RequiredArgsConstructor
public class SupplierPaymentController {

    private final SupplierPaymentService supplierPaymentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<SupplierpaymentResponseDTO>>> getAllCustomerPayments(@RequestParam(required = false) HashMap<String, String> filters) {
        List<SupplierpaymentResponseDTO> supplierPayments = supplierPaymentService.getAllSupplierPayments(filters);
        return ApiResponse.successResponse(supplierPayments);
    }

    @GetMapping(path = "/supplier-payment-info/{supplierBrno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<SupplierpaymentInfoDTO>> getCustomerPaymentDetails(@PathVariable String supplierBrno) {
        SupplierpaymentInfoDTO supplierpaymentInfoDTO = supplierPaymentService.getSupplierPaymentInfo(supplierBrno);
        return ApiResponse.successResponse(supplierpaymentInfoDTO);
    }


    @GetMapping(path = "/supplier-payment-code/{supplierBrno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getSupplierPaymentCode(@PathVariable String supplierBrno) {
        Map<String, String> response = new HashMap<>();
        response.put("supplierPaymentCode", supplierPaymentService.getSupplierPaymentCode(supplierBrno));
        return ApiResponse.successResponse(response);
    }


    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveSupplierPayment(@Valid @RequestBody SupplierpaymentRequestDTO supplierpaymentRequestDTO) {
        Supplierpayment saveSupplierPayment = supplierPaymentService.saveSupplierPayment(supplierpaymentRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Supplier Payment : " + saveSupplierPayment.getCode()
        );
    }

//    @PreAuthorize("hasAuthority('employee-update')")
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateCustomer(@Valid @RequestBody SupplierpaymentRequestDTO supplierpaymentRequestDTO) {
        Supplierpayment updatedCustomerPayment = supplierPaymentService.updateSupplierPayment(supplierpaymentRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Supplier Payment : " + updatedCustomerPayment.getCode()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable Integer id) {
        supplierPaymentService.deleteSupplierPayment(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Supplier Payment ID : " + id
        );
    }
}
