package lk.travel.travellion.service.transfer;

import lk.travel.travellion.dto.transfercontractdto.TransfertypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.TransfertypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferTypeService {

    private final TransfertypeRepository transfertypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<TransfertypeDTO> getAllTransferTypes() {
        return objectMapper.toTransferTypeDTOs(transfertypeRepository.findAll());
    }
}
