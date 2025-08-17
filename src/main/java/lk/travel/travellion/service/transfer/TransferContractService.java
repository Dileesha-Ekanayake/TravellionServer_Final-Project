package lk.travel.travellion.service.transfer;

import lk.travel.travellion.dto.transfercontractdto.TransferContractSearchDTO;
import lk.travel.travellion.dto.transfercontractdto.TransfercontractRequestDTO;
import lk.travel.travellion.dto.transfercontractdto.TransfercontractResponseDTO;
import lk.travel.travellion.entity.Transfercontract;

import java.util.HashMap;
import java.util.List;

public interface TransferContractService {
    
    List<TransfercontractResponseDTO> getAllTransferContracts(HashMap<String, String> filters);

    List<TransfercontractResponseDTO> searchTransferContracts(TransferContractSearchDTO transferContractSearchDTO);
    
    String getTransferContractRefNumber(String supplierBrNo);

    Transfercontract saveTransferContract(TransfercontractRequestDTO transfercontractRequestDTO);

    void saveTransferContract(List<TransfercontractRequestDTO> transfercontractRequestDTOs);

    Transfercontract updateTransferContract(TransfercontractRequestDTO transfercontractRequestDTO);

    void deleteTransferContract(Integer id);

    void deleteAllContracts();
}
