package lk.travel.travellion.service.auth;

import lk.travel.travellion.dto.moduledto.ModuleDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.ModuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<ModuleDTO> getAllModules() {
        return objectMapper.toModuleDTOs(moduleRepository.findAll());
    }
}
