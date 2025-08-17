package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.transfercontractdto.TransfertypeDTO;
import lk.travel.travellion.service.transfer.TransferTypeService;
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
@RequestMapping("/transfertypes")
@RequiredArgsConstructor
public class TransferTypeController {

    private final TransferTypeService transferTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<TransfertypeDTO>>> getTransferTypes() {
        return ApiResponse.successResponse(transferTypeService.getAllTransferTypes());
    }
}
