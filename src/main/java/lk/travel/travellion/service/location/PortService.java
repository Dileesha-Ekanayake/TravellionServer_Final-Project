package lk.travel.travellion.service.location;

import lk.travel.travellion.dto.locationdto.PortListDTO;
import lk.travel.travellion.dto.locationdto.PortResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.PortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortService {

    private final PortRepository portRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<PortResponseDTO> getAllPorts() {
        return objectMapper.toPortResponseDTOs(portRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<PortListDTO> getAllPortList() {
        return objectMapper.toPortListDTOs(portRepository.findAll());
    }
}
