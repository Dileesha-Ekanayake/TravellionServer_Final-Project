package lk.travel.travellion.service.accommodation;

import lk.travel.travellion.dto.accommodationdto.AccommodationRequestDTO;
import lk.travel.travellion.dto.accommodationdto.AccommodationResponseDTO;
import lk.travel.travellion.dto.accommodationdto.AccommodationSearchDTO;
import lk.travel.travellion.entity.Accommodation;
import lk.travel.travellion.service.booking.RoomCountDTO;

import java.util.HashMap;
import java.util.List;

public interface AccommodationService {
   
    List<AccommodationResponseDTO> getAllAccommodations(HashMap<String, String> filters);

    List<AccommodationResponseDTO> searchAccommodations(AccommodationSearchDTO accommodationSearchDTO);

    List<RoomCountDTO> getAllRoomsCountByAccommodationIdAndRoomTypesList(int accommodationId, List<String> roomTypes);

    String getAccommodationRefNumber(String supplierBrNo);

    Accommodation saveAccommodation(AccommodationRequestDTO accommodationRequestDTO);

    Accommodation updateAccommodation(AccommodationRequestDTO accommodationRequestDTO);

    void deleteAccommodation(Integer id);
}
