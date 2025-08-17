package lk.travel.travellion.service.user;

import lk.travel.travellion.dto.userdto.UsertypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<UsertypeDTO> getAllUserTypes() {
        return objectMapper.toUserTypeDTOs(userTypeRepository.findAll());
    }
}
