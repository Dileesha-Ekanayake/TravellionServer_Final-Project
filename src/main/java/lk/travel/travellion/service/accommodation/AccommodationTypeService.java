package lk.travel.travellion.service.accommodation;

import lk.travel.travellion.dto.accommodationdto.AccommodationtypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.AccommodationtypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationTypeService {

    private final AccommodationtypeRepository accommodationtypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<AccommodationtypeDTO> getAllAccommodationTypes() {
        return objectMapper.toAccommodationtypeDTOs(accommodationtypeRepository.findAll());
    }

}
