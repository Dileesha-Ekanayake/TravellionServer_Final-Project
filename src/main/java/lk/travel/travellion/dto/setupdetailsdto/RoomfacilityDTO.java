package lk.travel.travellion.dto.setupdetailsdto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link lk.travel.travellion.entity.Roomfacility}
 */
@Value
public class RoomfacilityDTO implements Serializable {
    Integer id;
    String name;
}
