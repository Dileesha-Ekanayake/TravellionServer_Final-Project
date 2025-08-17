package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.locationdto.LocationListDTO;
import lk.travel.travellion.dto.locationdto.LocationRequestDTO;
import lk.travel.travellion.dto.locationdto.LocationResponseDTO;
import lk.travel.travellion.entity.Location;
import lk.travel.travellion.service.location.LocationService;
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
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<LocationResponseDTO>>> getLocations(@RequestParam(required = false) HashMap<String, String> filters) {
        List<LocationResponseDTO> locations = locationService.getAllLocations(filters);
        return ApiResponse.successResponse(locations);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<LocationListDTO>>> getLocationList() {
        List<LocationListDTO> locations = locationService.getAllLocationList();
        return ApiResponse.successResponse(locations);
    }

    @GetMapping(path = "/location-code/{cityCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getLocationCode(@PathVariable String cityCode) {
        Map<String, String> response = new HashMap<>();
        response.put("locationCode", locationService.getLocationCode(cityCode));
        return ApiResponse.successResponse(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody LocationRequestDTO locationRequestDTO) {
        Location savedLocation = locationService.saveLocation(locationRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Location : " + savedLocation.getName()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody LocationRequestDTO locationRequestDTO) {
        Location updatedLocation = locationService.updateLocation(locationRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Location : " + updatedLocation.getName()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Integer id) {
        locationService.deleteLocation(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Location ID : " + id
        );
    }
}
