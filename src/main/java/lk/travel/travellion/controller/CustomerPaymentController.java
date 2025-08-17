package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.customerpaymentdto.CustomerpaymentRequestDTO;
import lk.travel.travellion.dto.customerpaymentdto.CustomerpaymentResponseDTO;
import lk.travel.travellion.entity.Customerpayment;
import lk.travel.travellion.service.customerpayment.CustomerPaymentService;
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
@RequestMapping("/customerpayments")
@RequiredArgsConstructor
public class CustomerPaymentController {

    private final CustomerPaymentService customerPaymentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<CustomerpaymentResponseDTO>>> getAllCustomerPayments(@RequestParam(required = false) HashMap<String, String> filters) {
        List<CustomerpaymentResponseDTO> customerPayments = customerPaymentService.getAllCustomerPayments(filters);
        return ApiResponse.successResponse(customerPayments);
    }


    @GetMapping(path = "/customer-payment-code/{customerCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getCustomerCode(@PathVariable String customerCode) {
        Map<String, String> response = new HashMap<>();
        response.put("customerPaymentCode", customerPaymentService.getCustomerPaymentCode(customerCode));
        return ApiResponse.successResponse(response);
    }


    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveCustomer(@Valid @RequestBody CustomerpaymentRequestDTO customerpaymentRequestDTO) {
        Customerpayment savedCustomerPayment = customerPaymentService.saveCustomerPayment(customerpaymentRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Customer Payment : " + savedCustomerPayment.getCode()
        );
    }

//    @PreAuthorize("hasAuthority('employee-update')")
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateCustomer(@Valid @RequestBody CustomerpaymentRequestDTO customerpaymentRequestDTO) {
        Customerpayment updatedCustomerPayment = customerPaymentService.updateCustomerPayment(customerpaymentRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Customer Payment : " + updatedCustomerPayment.getCode()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable Integer id) {
        customerPaymentService.deleteCustomerPayment(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Customer Payment ID : " + id
        );
    }
}
