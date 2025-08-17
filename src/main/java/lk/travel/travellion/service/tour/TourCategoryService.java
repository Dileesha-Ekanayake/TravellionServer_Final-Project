
package lk.travel.travellion.service.tour;

import lk.travel.travellion.dto.tourcontractdto.TourcategoryDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.TourcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourCategoryService {

    private final TourcategoryRepository tourcategoryRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<TourcategoryDTO> getAllTourCategories() {
        return objectMapper.toTourCategoryDTOs(tourcategoryRepository.findAll());
    }
}
