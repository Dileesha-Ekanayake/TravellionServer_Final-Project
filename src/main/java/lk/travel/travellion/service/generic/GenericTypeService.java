package lk.travel.travellion.service.generic;

import lk.travel.travellion.dto.genericdto.GenerictypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.GenerictypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenericTypeService {

    private final GenerictypeRepository generictypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<GenerictypeDTO> getAllGenericTypes() {
        return objectMapper.toGenericTypeDTOs(generictypeRepository.findAll());
    }
}
