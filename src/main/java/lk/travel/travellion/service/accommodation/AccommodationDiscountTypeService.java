package lk.travel.travellion.service.accommodation;

import lk.travel.travellion.dto.accommodationdto.AccommodationdiscounttypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.AccommodationdiscounttypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationDiscountTypeService {

    private final AccommodationdiscounttypeRepository accommodationdiscounttypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<AccommodationdiscounttypeDTO> getAccommodationdiscounttypes() {
        return objectMapper.toAccommodationdiscounttypeDTOs(accommodationdiscounttypeRepository.findAll());
    }

}
