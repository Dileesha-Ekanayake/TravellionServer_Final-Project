package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.operationdto.OperationDTO;
import lk.travel.travellion.entity.Operation;
import lk.travel.travellion.service.auth.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<OperationDTO>>> getAllOperations() {
        List<OperationDTO> operations = operationService.getAllOperations();
        return ApiResponse.successResponse(operations);
    }

    @GetMapping(path = "/operations-by-moduleId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<OperationDTO>>> getAllOperationsByModuleId(@PathVariable Integer id) {
        List<OperationDTO> operations = operationService.getOperationByModuleId(id);
        return ApiResponse.successResponse(operations);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveOperation(@Valid @RequestBody OperationDTO operation) {
        Operation savedOperation = operationService.saveOperation(operation);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Operation : " + savedOperation.getOperation()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteOperation(@PathVariable Integer id) {
        operationService.deleteOperation(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Operation ID : " + id
        );
    }
}
