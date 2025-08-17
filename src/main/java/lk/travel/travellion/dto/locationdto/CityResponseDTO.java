package lk.travel.travellion.dto.locationdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * DTO for {@link lk.travel.travellion.entity.City}
 */
@Value
public class CityResponseDTO implements Serializable {
    Integer id;
    UserDto user;
    @Size(max = 18)
    String code;
    @Size(max = 45)
    String name;
    byte[] photo;
    Timestamp createdon;
    Timestamp updatedon;
    DistrictDto district;
    Set<AirportResponseDTO> airports;
    Set<PortResponseDTO> ports;

    /**
     * DTO for {@link lk.travel.travellion.entity.User}
     */
    @Value
    public static class UserDto implements Serializable {
        Integer id;
        String username;
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
