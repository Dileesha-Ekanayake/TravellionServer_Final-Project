package lk.travel.travellion.dto.accommodationdto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link lk.travel.travellion.entity.Accommodation}
 */
@Value
@Getter
@Setter
public class AccommodationSearchDTO {

    String name;
    String location;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    Integer adultsCount;
    Integer childrenCount;
    Integer roomsCount;
    List<String> roomTypes;

}
