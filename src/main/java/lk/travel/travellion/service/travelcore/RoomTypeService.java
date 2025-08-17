package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.RoomtypeDTO;
import lk.travel.travellion.entity.Roomtype;

import java.util.List;

public interface RoomTypeService {
    
    List<RoomtypeDTO> getAllRoomTypes();

    Roomtype saveRoomType(RoomtypeDTO roomtypeDTO);

    Roomtype updateRoomType(RoomtypeDTO roomtypeDTO);

    void deleteRoomType(Integer id);
}
