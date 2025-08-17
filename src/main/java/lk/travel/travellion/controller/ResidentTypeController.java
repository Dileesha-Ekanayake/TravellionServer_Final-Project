package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.setupdetailsdto.ResidenttypeDTO;
import lk.travel.travellion.entity.Residenttype;
import lk.travel.travellion.service.travelcore.ResidentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/residenttypes")
@RequiredArgsConstructor
public class ResidentTypeController {

    private final ResidentTypeService residentTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<ResidenttypeDTO>>> getResidentTypes() {
        List<ResidenttypeDTO> residenttypes = residentTypeService.getAllResidentTypes();
        return ApiResponse.successResponse(residenttypes);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody ResidenttypeDTO residenttypeDTO) {
        Residenttype savedResidentType = residentTypeService.saveResidentType(residenttypeDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "ResidentType : " + savedResidentType.getName()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody ResidenttypeDTO residenttypeDTO) {
        Residenttype updateResidentType = residentTypeService.updateResidentType(residenttypeDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "ResidentType : " + updateResidentType.getName()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        residentTypeService.deleteResidentType(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "ResidentType ID : " + id
        );
    }
}
