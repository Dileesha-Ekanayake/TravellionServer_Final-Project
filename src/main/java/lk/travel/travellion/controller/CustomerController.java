package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.customerdto.CustomerListDTO;
import lk.travel.travellion.dto.customerdto.CustomerRequestDTO;
import lk.travel.travellion.dto.customerdto.CustomerResponseDTO;
import lk.travel.travellion.entity.Customer;
import lk.travel.travellion.service.customer.CustomerService;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getAllCustomers(@RequestParam(required = false) HashMap<String, String> filters) {
        List<CustomerResponseDTO> customers = customerService.getAllCustomers(filters);
        return ApiResponse.successResponse(customers);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<CustomerListDTO>>> getCustomerList() {
        List<CustomerListDTO> customers = customerService.getAllCustomerList();
        return ApiResponse.successResponse(customers);
    }

    @GetMapping(path = "/customer-code", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getCustomerCode() {
        Map<String, String> response = new HashMap<>();
        response.put("customerCode", customerService.getCustomerCode());
        return ApiResponse.successResponse(response);
    }


    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        Customer savedCustomer = customerService.saveCustomer(customerRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Customer : " + savedCustomer.getCode()
        );
    }

//    @PreAuthorize("hasAuthority('employee-update')")
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateCustomer(@Valid @RequestBody CustomerRequestDTO customerRequestDTO) {
        Customer updatedCustomer = customerService.updateCustomer(customerRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Customer : " + updatedCustomer.getCode()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Customer ID : " + id
        );
    }
}
