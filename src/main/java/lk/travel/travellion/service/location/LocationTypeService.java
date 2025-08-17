package lk.travel.travellion.service.location;

import lk.travel.travellion.dto.transfercontractdto.LocationtypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.LocationtypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationTypeService {

    private final LocationtypeRepository locationtypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<LocationtypeDTO> getAllLocationTypes() {
        return objectMapper.toLocationTypeDTOs(locationtypeRepository.findAll());
    }
}
