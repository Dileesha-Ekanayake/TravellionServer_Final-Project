package lk.travel.travellion.service.location;

import lk.travel.travellion.dto.locationdto.LocationListDTO;
import lk.travel.travellion.dto.locationdto.LocationRequestDTO;
import lk.travel.travellion.dto.locationdto.LocationResponseDTO;
import lk.travel.travellion.entity.Location;

import java.util.HashMap;
import java.util.List;

public interface LocationService {
    
    List<LocationResponseDTO> getAllLocations(HashMap<String, String> filters);

    List<LocationListDTO> getAllLocationList();

    String getLocationCode(String citiCode);

    Location saveLocation(LocationRequestDTO locationRequestDTO);

    Location updateLocation(LocationRequestDTO locationRequestDTO);

    void deleteLocation(Integer id);
}
