package lk.travel.travellion.service.generic;

import lk.travel.travellion.dto.genericdto.GenericdiscounttypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.GenericdiscounttypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenericDiscountTypeService {

    private final GenericdiscounttypeRepository genericdiscounttypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<GenericdiscounttypeDTO> getAllGenericDiscountTypes() {
        return objectMapper.toGenericDiscountTypeDTOs(genericdiscounttypeRepository.findAll());
    }
}
