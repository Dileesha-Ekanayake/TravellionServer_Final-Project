package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.tourcontractdto.TourcontractRequestDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractResponseDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractSearchDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractViewResponseDTO;
import lk.travel.travellion.entity.Tourcontract;
import lk.travel.travellion.service.tour.TourContractService;
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
@RequestMapping("/tours")
@RequiredArgsConstructor
public class TourController {

    private final TourContractService tourContractService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<TourcontractResponseDTO>>> getTourContract(@RequestParam(required = false) HashMap<String, String> filters) {
        List<TourcontractResponseDTO> tourContracts = tourContractService.getAllTourContracts(filters);
        return ApiResponse.successResponse(tourContracts);
    }

    @GetMapping(path = "/next-tour-reference", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getTourContractReference() {
        Map<String, String> response = new HashMap<>();
        response.put("tourRef", tourContractService.getTourContractReference());
        return ApiResponse.successResponse(response);
    }

    @GetMapping(path = "/search-tours", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<TourcontractResponseDTO>>> getToursSearch(@ModelAttribute TourcontractSearchDTO tourcontractSearchDTO) {
        List<TourcontractResponseDTO> tours = tourContractService.searchTours(tourcontractSearchDTO);
        return ApiResponse.successResponse(tours);
    }

    @GetMapping(path = "/tour-view/{tourReference}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<TourcontractViewResponseDTO>> getTourView(@PathVariable String tourReference) {
        TourcontractViewResponseDTO tours = tourContractService.getTourContractView(tourReference);
        return ApiResponse.successResponse(tours);
    }


    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody TourcontractRequestDTO tourcontractRequestDTO) {
        Tourcontract savedTourContract = tourContractService.saveTourContract(tourcontractRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Tour Contract : " + savedTourContract.getReference()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody TourcontractRequestDTO tourcontractRequestDTO) {
        Tourcontract updatedTourContract = tourContractService.updateTourContract(tourcontractRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Tour Contract : " + updatedTourContract.getReference()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Integer id) {
        tourContractService.deleteTourContract(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Tour Contract ID : " + id
        );
    }
}
