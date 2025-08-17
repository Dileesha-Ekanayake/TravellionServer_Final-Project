package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.setupdetailsdto.CancellationschemeDTO;
import lk.travel.travellion.entity.Cancellationscheme;
import lk.travel.travellion.service.travelcore.CancellationSchemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cancellationschemes")
@RequiredArgsConstructor
public class CancellationSchemeController {

    private final CancellationSchemeService cancellationSchemeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<CancellationschemeDTO>>> getCancellationSchemes() {
        List<CancellationschemeDTO> cancellationSchemes = cancellationSchemeService.getAllCancellationSchemes();
        return ApiResponse.successResponse(cancellationSchemes);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody CancellationschemeDTO cancellationschemeDTO) {
        Cancellationscheme savedCancellationScheme = cancellationSchemeService.saveCancellationScheme(cancellationschemeDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "CancellationScheme : " + savedCancellationScheme.getName()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody CancellationschemeDTO cancellationschemeDTO) {
        Cancellationscheme updatedCancellationScheme = cancellationSchemeService.updateCancellationScheme(cancellationschemeDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "CancellationScheme : " + updatedCancellationScheme.getName()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        cancellationSchemeService.deleteCancellationScheme(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "CancellationScheme ID : " + id
        );
    }
}
