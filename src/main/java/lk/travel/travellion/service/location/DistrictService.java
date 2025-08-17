package lk.travel.travellion.service.location;

import lk.travel.travellion.dto.locationdto.DistrictResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<DistrictResponseDTO> getAllDistricts() {
        return objectMapper.toDistrictResponseDTOs(districtRepository.findAll());
    }
}
