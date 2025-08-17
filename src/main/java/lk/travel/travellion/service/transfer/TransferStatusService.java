package lk.travel.travellion.service.transfer;

import lk.travel.travellion.dto.transfercontractdto.TransferstatusDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.TransferstatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferStatusService {

    private final TransferstatusRepository transferstatusRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<TransferstatusDTO> getAllTransferContractStatus() {
        return objectMapper.toTransferStatusDTOs(transferstatusRepository.findAll());
    }
}
