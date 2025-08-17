package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.locationdto.DistrictResponseDTO;
import lk.travel.travellion.service.location.DistrictService;
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
@RequestMapping("/districts")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<DistrictResponseDTO>>> getDistricts() {
        List<DistrictResponseDTO> districtResponseDTOS = districtService.getAllDistricts();
        return ApiResponse.successResponse(districtResponseDTOS);
    }
}
