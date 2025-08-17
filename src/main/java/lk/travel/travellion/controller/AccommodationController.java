package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.accommodationdto.AccommodationRequestDTO;
import lk.travel.travellion.dto.accommodationdto.AccommodationResponseDTO;
import lk.travel.travellion.dto.accommodationdto.AccommodationSearchDTO;
import lk.travel.travellion.entity.Accommodation;
import lk.travel.travellion.service.accommodation.AccommodationService;
import lk.travel.travellion.service.booking.RoomCountDTO;
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
@RequestMapping("/accommodations")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<AccommodationResponseDTO>>> getAccommodations(@RequestParam(required = false) HashMap<String, String> filters) {
        List<AccommodationResponseDTO> accommodations = accommodationService.getAllAccommodations(filters);
        return ApiResponse.successResponse(accommodations);
    }

    @GetMapping(path = "/get-next-reference/{supplierBrno}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getAccommodationReference(@PathVariable String supplierBrno) {
        Map<String, String> response = new HashMap<>();
        response.put("accommRef", accommodationService.getAccommodationRefNumber(supplierBrno));
        return ApiResponse.successResponse(response);
    }

    @GetMapping(path = "/available-room-count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RoomCountDTO>>> getRoomCount(@RequestParam Integer accommId, @RequestParam List<String> roomTypes) {
        List<RoomCountDTO> bookedROomCount = accommodationService.getAllRoomsCountByAccommodationIdAndRoomTypesList(accommId, roomTypes);
        return ApiResponse.successResponse(bookedROomCount);
    }

    @GetMapping(path = "/search-accommodations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<AccommodationResponseDTO>>> getAccommodationSearch(@ModelAttribute AccommodationSearchDTO accommodationSearchDTO) {
        List<AccommodationResponseDTO> accommodations = accommodationService.searchAccommodations(accommodationSearchDTO);
        return ApiResponse.successResponse(accommodations);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody AccommodationRequestDTO accommodationRequestDTO) {
        Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodationRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Accommodation : " + savedAccommodation.getReference()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody AccommodationRequestDTO accommodationRequestDTO) {
        Accommodation updatedAccommodation = accommodationService.updateAccommodation(accommodationRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Accommodation : " + updatedAccommodation.getReference()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Integer id) {
        accommodationService.deleteAccommodation(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Accommodation ID : " + id
        );
    }
}
