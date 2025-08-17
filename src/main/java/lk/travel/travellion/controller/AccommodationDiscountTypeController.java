package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.accommodationdto.AccommodationdiscounttypeDTO;
import lk.travel.travellion.service.accommodation.AccommodationDiscountTypeService;
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
@RequestMapping("/accommodationdiscounttypes")
@RequiredArgsConstructor
public class AccommodationDiscountTypeController {

    private final AccommodationDiscountTypeService accommodationDiscountTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<AccommodationdiscounttypeDTO>>> getAccommodationDiscountTypes() {
        return ApiResponse.successResponse(accommodationDiscountTypeService.getAccommodationdiscounttypes());
    }

}
