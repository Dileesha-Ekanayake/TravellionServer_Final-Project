package lk.travel.travellion.dto.locationdto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.District}
 */
@Value
public class DistrictResponseDTO implements Serializable {
    Integer id;
    @Size(max = 6)
    String code;
    @Size(max = 45)
    String name;
    ProvinceResponseDTO province;
}
