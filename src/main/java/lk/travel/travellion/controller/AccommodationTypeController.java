package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.accommodationdto.AccommodationtypeDTO;
import lk.travel.travellion.service.accommodation.AccommodationTypeService;
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
@RequestMapping("/accommodationtypes")
@RequiredArgsConstructor
public class AccommodationTypeController {

    private final AccommodationTypeService accommodationTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<AccommodationtypeDTO>>> getAccommodationTypes() {
        return ApiResponse.successResponse(accommodationTypeService.getAllAccommodationTypes());
    }

}
