package lk.travel.travellion.service.transfer;

import lk.travel.travellion.dto.transfercontractdto.TransferdiscounttypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.TransferdiscounttypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferDiscountTypeService {

    private final TransferdiscounttypeRepository transferdiscounttypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<TransferdiscounttypeDTO> getAllTransferDiscountTypes() {
        return objectMapper.toTransferDiscountTypeDTOs(transferdiscounttypeRepository.findAll());
    }
}
