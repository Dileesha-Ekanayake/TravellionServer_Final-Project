package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.transfercontractdto.TransferdiscounttypeDTO;
import lk.travel.travellion.service.transfer.TransferDiscountTypeService;
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
@RequestMapping("/transferdiscounttypes")
@RequiredArgsConstructor
public class TransferDiscountTypeController {

    private final TransferDiscountTypeService transferDiscountTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<TransferdiscounttypeDTO>>> getTransferStatuses() {
        return ApiResponse.successResponse(transferDiscountTypeService.getAllTransferDiscountTypes());
    }
}
