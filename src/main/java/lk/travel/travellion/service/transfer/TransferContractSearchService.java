package lk.travel.travellion.service.transfer;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lk.travel.travellion.dto.transfercontractdto.TransferContractSearchDTO;
import lk.travel.travellion.dto.transfercontractdto.TransfercontractResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Pickuplocation;
import lk.travel.travellion.entity.Transfer;
import lk.travel.travellion.entity.Transfercontract;
import lk.travel.travellion.entity.Transfertype;
import lk.travel.travellion.repository.TransfercontractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferContractSearchService {

    private final TransfercontractRepository transfercontractRepository;
    private final ObjectMapper objectMapper;

    /**
     * Searches for transfer contracts based on the specified search criteria.
     *
     * @param transferContractSearchDTO the data transfer object containing search criteria such as
     *                                   pickup location, drop-off location, transfer type, and return status
     * @return a list of TransfercontractResponseDTO objects that match the search criteria
     */
    @Transactional(readOnly = true)
    public List<TransfercontractResponseDTO> searchTransferContract(TransferContractSearchDTO transferContractSearchDTO) {

        Specification<Transfercontract> spec = Specification.where(null);

        if (transferContractSearchDTO.getPickuplocation() != null && transferContractSearchDTO.getDroplocation() != null) {
            spec = spec.and((root, cQuery, cb) -> {
                Join<Transfercontract, Transfer> transferJoin = root.join("transfer", JoinType.INNER);
                Join<Transfer, Pickuplocation> pickupLocationJoin = transferJoin.join("pickuplocations", JoinType.LEFT);
                Join<Transfer, Pickuplocation> dropLocationJoin = transferJoin.join("droplocations", JoinType.LEFT);

                Predicate pickupPredicate = cb.like(cb.lower(pickupLocationJoin.get("name")),
                        "%" + transferContractSearchDTO.getPickuplocation().toLowerCase() + "%");
                Predicate dropPredicate = cb.like(cb.lower(dropLocationJoin.get("name")),
                        "%" + transferContractSearchDTO.getDroplocation().toLowerCase() + "%");

                return cb.or(pickupPredicate, dropPredicate); // Match either pickup OR drop
            });
        } else if (transferContractSearchDTO.getPickuplocation() != null) {
            spec = spec.and((root, cQuery, cb) -> {
                Join<Transfercontract, Transfer> transferJoin = root.join("transfer", JoinType.INNER);
                Join<Transfer, Pickuplocation> pickupLocationJoin = transferJoin.join("pickuplocations", JoinType.LEFT);

                return cb.like(cb.lower(pickupLocationJoin.get("name")),
                        "%" + transferContractSearchDTO.getPickuplocation().toLowerCase() + "%");
            });
        } else {
            spec = spec.and((root, cQuery, cb) -> {
                Join<Transfercontract, Transfer> transferJoin = root.join("transfer", JoinType.INNER);
                Join<Transfer, Pickuplocation> dropLocationJoin = transferJoin.join("droplocations", JoinType.LEFT);

                return cb.like(cb.lower(dropLocationJoin.get("name")),
                        "%" + transferContractSearchDTO.getDroplocation().toLowerCase() + "%");
            });
        }



        if (transferContractSearchDTO.getTransfertype() != null) {
            spec = spec.and((root, cQuery, cb) -> {
                Join<Transfercontract, Transfertype> transferTypeJoin = root.join("transfertype", JoinType.INNER);
                return cb.equal(transferTypeJoin.get("name"), transferContractSearchDTO.getTransfertype().toLowerCase());
            });
        }

        if (transferContractSearchDTO.getIsreturn() != null) {
            spec = spec.and((root, cQuery, cb) -> {
                Join<Transfercontract, Transfer> transferJoin = root.join("transfer", JoinType.INNER);
                return cb.equal(transferJoin.get("isreturn"), transferContractSearchDTO.getIsreturn());
            });
        }

        return objectMapper.toTransferContractResponseDTOs(transfercontractRepository.findAll(spec));
    }

}

