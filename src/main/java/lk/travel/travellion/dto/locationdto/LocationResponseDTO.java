package lk.travel.travellion.dto.locationdto;

import jakarta.validation.constraints.Size;
import lk.travel.travellion.entity.District;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.Location}
 */
@Value
public class LocationResponseDTO implements Serializable {
    Integer id;
    UserDTO user;
    @Size(max = 26)
    String code;
    @Size(max = 45)
    String name;
    byte[] photo;
    String description;
    Timestamp createdon;
    Timestamp updatedon;
    CityDto city;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDTO implements Serializable {
        Integer id;
        String username;
    }


    /**
     * DTO for {@link lk.travel.travellion.entity.City}
     */
    @Value
    public static class CityDto implements Serializable {
        Integer id;
        String code;
        String name;
        DistrictDto district;
        Set<AirportResponseDTO> airports;
        Set<PortResponseDTO> ports;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.District}
     */
    @Value
    public static class DistrictDto implements Serializable {
        Integer id;
        String code;
        String name;
        ProvinceDto province;
    }

    /**
     * DTO for {@link lk.travel.travellion.entity.Province}
     */
    @Value
    public static class ProvinceDto implements Serializable {
        Integer id;
        String code;
        String name;
    }
}
