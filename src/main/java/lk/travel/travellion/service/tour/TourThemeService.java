
package lk.travel.travellion.service.tour;

import lk.travel.travellion.dto.tourcontractdto.TourthemeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.TourthemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourThemeService {

    private final TourthemeRepository tourthemeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<TourthemeDTO> getAllTourThemes() {
        return objectMapper.toTourThemeDTOs(tourthemeRepository.findAll());
    }
}
