package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.RoomfacilityDTO;
import lk.travel.travellion.entity.Roomfacility;

import java.util.List;

public interface RoomFacilitiesService {
    
    List<RoomfacilityDTO> getAllRoomFacilities();

    Roomfacility saveRoomFacility(RoomfacilityDTO roomfacilityDTO);

    Roomfacility updateRoomFacility(RoomfacilityDTO roomfacilityDTO);

    void deleteRoomFacility(Integer id);
}
