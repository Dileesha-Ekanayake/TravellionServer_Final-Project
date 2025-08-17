package lk.travel.travellion.service.accommodation;

import lk.travel.travellion.dto.accommodationdto.AccommodationdiscountResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Accommodationdiscount;
import lk.travel.travellion.repository.AccommodationdiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationDiscountService {

    private final AccommodationdiscountRepository accommodationdiscountRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<AccommodationdiscountResponseDTO> getAllAccommodationDiscounts() {
        List<Accommodationdiscount> accommodationDiscounts = accommodationdiscountRepository.findAll();
        return objectMapper.toAccommodationdiscountResponseDTOs(accommodationDiscounts);
    }
}
