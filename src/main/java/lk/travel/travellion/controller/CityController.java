package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.locationdto.CityListDTO;
import lk.travel.travellion.dto.locationdto.CityRequestDTO;
import lk.travel.travellion.dto.locationdto.CityResponseDTO;
import lk.travel.travellion.entity.City;
import lk.travel.travellion.service.location.CityService;
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
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<CityResponseDTO>>> getAllCities(@RequestParam(required = false) HashMap<String, String> filters ) {
        List<CityResponseDTO> cityResponseDTOS = cityService.getAllCities(filters);
        return ApiResponse.successResponse(cityResponseDTOS);
    }

    @GetMapping(path = "/city-code", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getCityCode(@RequestParam(required = true) HashMap<String, String> data ) {
        Map<String, String> response = new HashMap<>();
        response.put("cityCode", cityService.getCityCode(data));
        return ApiResponse.successResponse(response);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<CityListDTO>>> getCityList() {
        List<CityListDTO> cityListDTOS = cityService.getAllCityList();
        return ApiResponse.successResponse(cityListDTOS);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveCity(@Valid @RequestBody CityRequestDTO cityRequestDTO) {
        City savedCity = cityService.saveCity(cityRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "City : " + savedCity.getCode()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateCity(@Valid @RequestBody CityRequestDTO cityRequestDTO) {
        City updatedCity = cityService.updateCity(cityRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "City : " + updatedCity.getCode()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCity(@PathVariable Integer id) {
        cityService.deleteCity(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "City ID : " + id
        );
    }
}
