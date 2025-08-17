package lk.travel.travellion.service.common;

import lk.travel.travellion.dto.genderdto.GenderDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.GenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenderService {

    private final GenderRepository genderRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<GenderDTO> getAllGenders(){
        return objectMapper.toGenderDTOs(genderRepository.findAll());
    }
}
