package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.setupdetailsdto.RatetypeDTO;
import lk.travel.travellion.entity.Ratetype;
import lk.travel.travellion.service.travelcore.RateTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/ratetypes")
@RequiredArgsConstructor
public class RateTypeController {

    private final RateTypeService rateTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RatetypeDTO>>> getRateTypes() {
        List<RatetypeDTO> ratetypes = rateTypeService.getAllRateTypes();
        return ApiResponse.successResponse(ratetypes);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody RatetypeDTO ratetypeDTO) {
        Ratetype savedRateType = rateTypeService.saveRateType(ratetypeDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "RateType : " + savedRateType.getName()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody RatetypeDTO ratetypeDTO) {
        Ratetype updatedRateType = rateTypeService.updateRateType(ratetypeDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "RateType : " + updatedRateType.getName()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        rateTypeService.deleteRateType(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "RateType ID : " + id
        );
    }
}
