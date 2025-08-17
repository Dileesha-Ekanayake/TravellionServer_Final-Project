package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.genericdto.GenericRequestDTO;
import lk.travel.travellion.dto.genericdto.GenericResponseDTO;
import lk.travel.travellion.dto.genericdto.GenericSearchDTO;
import lk.travel.travellion.entity.Generic;
import lk.travel.travellion.service.generic.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/generics")
@RequiredArgsConstructor
public class GenericController {

    private final GenericService genericService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<GenericResponseDTO>>> getGenerics(@RequestParam(required = false) HashMap<String, String> filters) {
        List<GenericResponseDTO> generics = genericService.getAllGenerics(filters);
        return ApiResponse.successResponse(generics);
    }

    @GetMapping(path = "/next-generic-reference", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getGenericReference() {
        Map<String, String> response = new HashMap<>();
        response.put("genericRef", genericService.getGenericReferenceNumber());
        return ApiResponse.successResponse(response);
    }

    @GetMapping(path = "/search-generics", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<GenericResponseDTO>>> getGenericsSearch(@ModelAttribute GenericSearchDTO genericSearchDTO) {
        List<GenericResponseDTO> generics = genericService.searchGenerics(genericSearchDTO);
        return ApiResponse.successResponse(generics);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody GenericRequestDTO genericRequestDTO) {
        Generic savedGeneric = genericService.saveGeneric(genericRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Generic : " + savedGeneric.getReference()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody GenericRequestDTO genericRequestDTO) {
        Generic updatedGeneric = genericService.updateGeneric(genericRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Generic : " + updatedGeneric.getReference()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Integer id) {
        genericService.deleteGeneric(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Generic ID : " + id
        );
    }
}
