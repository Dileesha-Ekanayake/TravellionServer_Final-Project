package lk.travel.travellion.service.user;

import lk.travel.travellion.dto.userdto.UserstatusDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStatusService {

    private final UserStatusRepository userStatusRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<UserstatusDTO> getAllUserStatus() {
        return objectMapper.toUserStatusDTOs(userStatusRepository.findAll());
    }
}
