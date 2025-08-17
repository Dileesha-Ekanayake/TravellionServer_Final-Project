package lk.travel.travellion.service.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class RoomCountDTO {

    private String roomType;
    private Integer count;
}
