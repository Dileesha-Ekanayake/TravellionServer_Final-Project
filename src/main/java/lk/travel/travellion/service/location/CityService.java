package lk.travel.travellion.service.location;

import lk.travel.travellion.dto.locationdto.CityListDTO;
import lk.travel.travellion.dto.locationdto.CityRequestDTO;
import lk.travel.travellion.dto.locationdto.CityResponseDTO;
import lk.travel.travellion.entity.City;

import java.util.HashMap;
import java.util.List;

public interface CityService {
    
    List<CityResponseDTO> getAllCities(HashMap<String, String> filters);

    String getCityCode(HashMap<String, String> data);

    List<CityListDTO> getAllCityList();

    City saveCity(CityRequestDTO cityRequestDTO);

    City updateCity(CityRequestDTO cityRequestDTO);

    void deleteCity(Integer id);
}
