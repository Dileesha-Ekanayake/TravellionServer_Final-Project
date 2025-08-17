package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.supplierdto.SupplierstatusDTO;
import lk.travel.travellion.service.supplier.SupplierStatusService;
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
@RequestMapping("/supplierstatuses")
@RequiredArgsConstructor
public class SupplierStatusController {

    private final SupplierStatusService supplierStatusService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<SupplierstatusDTO>>> getSupplierStatus() {
        return ApiResponse.successResponse(supplierStatusService.getAllSupplierStatus());
    }
}
