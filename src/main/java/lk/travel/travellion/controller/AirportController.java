package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.locationdto.AirportListDTO;
import lk.travel.travellion.dto.locationdto.AirportResponseDTO;
import lk.travel.travellion.service.location.AirportService;
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
@RequestMapping("/airports")
@RequiredArgsConstructor
public class AirportController {

    private final AirportService airportService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<AirportResponseDTO>>> getAirports() {
        List<AirportResponseDTO> airportResponseDTOS = airportService.getAllAirports();
        return ApiResponse.successResponse(airportResponseDTOS);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<AirportListDTO>>> getAirportList() {
        List<AirportListDTO> airportListDTOS = airportService.getAirportList();
        return ApiResponse.successResponse(airportListDTOS);
    }
}
