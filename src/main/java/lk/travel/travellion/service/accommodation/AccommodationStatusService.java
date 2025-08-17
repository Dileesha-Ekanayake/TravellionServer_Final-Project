package lk.travel.travellion.service.accommodation;

import lk.travel.travellion.dto.accommodationdto.AccommodationstatusDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.AccommodationstatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationStatusService {

    private final AccommodationstatusRepository accommodationstatusRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<AccommodationstatusDTO> getAllAccommodationStatus() {
        return objectMapper.toAccommodationstatusDTOs(accommodationstatusRepository.findAll());
    }

}
