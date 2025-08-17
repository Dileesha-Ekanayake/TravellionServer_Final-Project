package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.transfercontractdto.TransferContractSearchDTO;
import lk.travel.travellion.dto.transfercontractdto.TransfercontractRequestDTO;
import lk.travel.travellion.dto.transfercontractdto.TransfercontractResponseDTO;
import lk.travel.travellion.entity.Transfercontract;
import lk.travel.travellion.service.transfer.TransferContractService;
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
@RequestMapping("/transfercontract")
@RequiredArgsConstructor
public class TransferContractController {

    private final TransferContractService transferContractService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<TransfercontractResponseDTO>>> getTransferContracts(@RequestParam(required = false) HashMap<String, String> filters) {
        List<TransfercontractResponseDTO> transfercontractResponses = transferContractService.getAllTransferContracts(filters);
        return ApiResponse.successResponse(transfercontractResponses);
    }

    @GetMapping(path = "/get-next-reference/{supplierBrno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getAccommodationReference(@PathVariable String supplierBrno) {
        Map<String, String> response = new HashMap<>();
        response.put("transferRef", transferContractService.getTransferContractRefNumber(supplierBrno));
        return ApiResponse.successResponse(response);
    }

    @GetMapping(path = "/search-transfer" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<TransfercontractResponseDTO>>> searchTransfer(@ModelAttribute TransferContractSearchDTO transferContractSearchDTO) {
        List<TransfercontractResponseDTO> transferContractResponses = transferContractService.searchTransferContracts(transferContractSearchDTO);
        return ApiResponse.successResponse(transferContractResponses);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody TransfercontractRequestDTO transfercontractRequestDTO) {
        Transfercontract savedTransferContract = transferContractService.saveTransferContract(transfercontractRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Transfer Contract : " + savedTransferContract.getReference()
        );
    }

    @PostMapping(path = "/list")
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody List<TransfercontractRequestDTO> transfercontractRequestDTOs) {
        transferContractService.saveTransferContract(transfercontractRequestDTOs);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                ""
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody TransfercontractRequestDTO transfercontractRequestDTO) {
        Transfercontract updatedTransferContract = transferContractService.updateTransferContract(transfercontractRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Transfer Contract : " + updatedTransferContract.getReference()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Integer id) {
         transferContractService.deleteTransferContract(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Transfer Contract ID : " + id
        );
    }

    @DeleteMapping("/delete-All")
    public ResponseEntity<ApiResponse<String>> delete() {
         transferContractService.deleteAllContracts();
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                ""
        );
    }

}
