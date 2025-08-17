package lk.travel.travellion.service.auth;

import lk.travel.travellion.dto.userdto.RoleDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<RoleDTO> getAllRoles() {
        return objectMapper.toRoleDTOs(roleRepository.findAll());
    }
}
