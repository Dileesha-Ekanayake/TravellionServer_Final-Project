package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.genericdto.GenerictypeDTO;
import lk.travel.travellion.service.generic.GenericTypeService;
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
@RequestMapping("/generictypes")
@RequiredArgsConstructor
public class GenericTypeController {

    private final GenericTypeService genericTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<GenerictypeDTO>>> getGenericTypes() {
        return ApiResponse.successResponse(genericTypeService.getAllGenericTypes());
    }
}
