package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.tourcontractdto.TourcategoryDTO;
import lk.travel.travellion.service.tour.TourCategoryService;
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
@RequestMapping("/tourcategories")
@RequiredArgsConstructor
public class TourCategoryController {

    private final TourCategoryService tourCategoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<TourcategoryDTO>>> getTourCategories() {
        return ApiResponse.successResponse(tourCategoryService.getAllTourCategories());
    }
}
