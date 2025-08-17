package lk.travel.travellion.service.generic;

import lk.travel.travellion.dto.genericdto.GenericstatusDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.GenericstatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenericStatusService {

    private final GenericstatusRepository genericstatusRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<GenericstatusDTO> getAllGenericStatus() {
        return objectMapper.toGenericStatusDTOs(genericstatusRepository.findAll());
    }
}
