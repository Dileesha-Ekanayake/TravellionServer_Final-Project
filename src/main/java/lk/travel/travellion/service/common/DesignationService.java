package lk.travel.travellion.service.common;

import lk.travel.travellion.dto.designationdto.DesignationDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.DesignationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DesignationService {

    private final DesignationRepository designationRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<DesignationDTO> getAllDesignations() {
        return objectMapper.toDesignationDTOs(designationRepository.findAll());
    }
}
