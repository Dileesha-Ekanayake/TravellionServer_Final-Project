package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.supplierdto.SupplierListDTO;
import lk.travel.travellion.dto.supplierdto.SupplierRequestDTO;
import lk.travel.travellion.dto.supplierdto.SupplierResponseDTO;
import lk.travel.travellion.entity.Supplier;
import lk.travel.travellion.service.supplier.SupplierService;
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
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<SupplierResponseDTO>>> getSuppliers(@RequestParam(required = false) HashMap<String, String> filters) {
        List<SupplierResponseDTO> suppliers = supplierService.getAllSuppliers(filters);
        return ApiResponse.successResponse(suppliers);
    }

    @GetMapping(path = "/supplier-br-number", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getSupplierBrNumber(){
        Map<String, String> response = new HashMap<>();
        response.put("SupplierBrNumber", supplierService.getSupplierBrNo());
        return ApiResponse.successResponse(response);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<SupplierListDTO>>> getSupplierList(){
        List<SupplierListDTO> suppliers = supplierService.getAllSupplierList();
        return ApiResponse.successResponse(suppliers);
    }

    @GetMapping(path = "/accomm_sup_list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<SupplierListDTO>>> getAccommSupplierList(){
        List<SupplierListDTO> suppliers = supplierService.getAllAccommSupplierList();
        return ApiResponse.successResponse(suppliers);
    }

    @GetMapping(path = "/transfer_sup_list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<SupplierListDTO>>> getTransferSupplierList(){
        List<SupplierListDTO> suppliers = supplierService.getAllTransferSupplierList();
        return ApiResponse.successResponse(suppliers);
    }

    @GetMapping(path = "/generic_sup_list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<SupplierListDTO>>> getGenericSupplierList(){
        List<SupplierListDTO> suppliers = supplierService.getAllGenericSupplierList();
        return ApiResponse.successResponse(suppliers);
    }


    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody SupplierRequestDTO supplier) {
        Supplier savedSupplier = supplierService.saveSupplier(supplier);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Supplier : " + savedSupplier.getBrno()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody SupplierRequestDTO supplier) {
        Supplier updatedSupplier = supplierService.updateSupplier(supplier);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Supplier : " + updatedSupplier.getBrno()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Supplier ID : " + id
        );
    }
}
