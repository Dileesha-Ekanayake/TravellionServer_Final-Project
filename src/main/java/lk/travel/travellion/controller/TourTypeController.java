package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.tourcontractdto.TourtypeDTO;
import lk.travel.travellion.service.tour.TourTypeService;
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
@RequestMapping("/tourtypes")
@RequiredArgsConstructor
public class TourTypeController {

    private final TourTypeService tourTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<TourtypeDTO>>> getTourTypes() {
        return ApiResponse.successResponse(tourTypeService.getAllTourTypes());
    }
}
