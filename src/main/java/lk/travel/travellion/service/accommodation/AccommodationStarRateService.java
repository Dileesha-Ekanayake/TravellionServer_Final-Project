package lk.travel.travellion.service.accommodation;

import lk.travel.travellion.dto.accommodationdto.StarratingDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.StarratingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationStarRateService {

    private final StarratingRepository starratingRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<StarratingDTO> getAllAccommodationStarRates() {
        return objectMapper.toStarratingDTOs(starratingRepository.findAll());
    }

}
