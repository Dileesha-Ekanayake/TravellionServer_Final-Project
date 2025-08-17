package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.transfercontractdto.LocationtypeDTO;
import lk.travel.travellion.service.location.LocationTypeService;
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
@RequestMapping("/locationtypes")
@RequiredArgsConstructor
public class LocationTypeController {

    private final LocationTypeService locationtypeService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<LocationtypeDTO>>> getLocationTypes() {
        return ApiResponse.successResponse(locationtypeService.getAllLocationTypes());
    }
}
