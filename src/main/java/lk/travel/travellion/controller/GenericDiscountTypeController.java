package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.genericdto.GenericdiscounttypeDTO;
import lk.travel.travellion.service.generic.GenericDiscountTypeService;
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
@RequestMapping("/genericdiscounttypes")
@RequiredArgsConstructor
public class GenericDiscountTypeController {

    private final GenericDiscountTypeService genericDiscountTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<GenericdiscounttypeDTO>>> getGenericDiscountTypes() {
        return ApiResponse.successResponse(genericDiscountTypeService.getAllGenericDiscountTypes());
    }
}
