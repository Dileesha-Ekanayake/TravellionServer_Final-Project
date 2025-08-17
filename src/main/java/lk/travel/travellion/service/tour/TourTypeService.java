package lk.travel.travellion.service.tour;

import lk.travel.travellion.dto.tourcontractdto.TourtypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.TourtypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourTypeService {

    private final TourtypeRepository tourtypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<TourtypeDTO> getAllTourTypes() {
        return objectMapper.toTourTypeDTOs(tourtypeRepository.findAll());
    }
}
