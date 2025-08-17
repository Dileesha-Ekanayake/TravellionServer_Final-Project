package lk.travel.travellion.service.location;

import lk.travel.travellion.dto.locationdto.ProvinceResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceService {

    private final ProvinceRepository provinceRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<ProvinceResponseDTO> getAllProvinces() {
        return objectMapper.toProvinceResponseDTOs(provinceRepository.findAll());
    }
}
