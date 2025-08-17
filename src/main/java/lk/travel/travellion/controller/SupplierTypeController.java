package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.supplierdto.SuppliertypeDTO;
import lk.travel.travellion.service.supplier.SupplierTypeService;
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
@RequestMapping("/suppliertypes")
@RequiredArgsConstructor
public class SupplierTypeController {

    private final SupplierTypeService supplierTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<SuppliertypeDTO>>> getSupplierTypes() {
        return ApiResponse.successResponse(supplierTypeService.getAllSupplierTypes());
    }
}
