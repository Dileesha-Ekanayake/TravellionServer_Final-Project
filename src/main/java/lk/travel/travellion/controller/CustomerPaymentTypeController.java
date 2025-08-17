package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.customerpaymentdto.PaymenttypeDTO;
import lk.travel.travellion.service.customerpayment.CustomerPaymentTypeService;
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
@RequestMapping("/customerpaymenttypes")
@RequiredArgsConstructor
public class CustomerPaymentTypeController {

    private final CustomerPaymentTypeService customerPaymentTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<PaymenttypeDTO>>> getCustomerPaymentTypes() {
        return ApiResponse.successResponse(customerPaymentTypeService.getAllCustomerPaymentTypes());
    }
}
