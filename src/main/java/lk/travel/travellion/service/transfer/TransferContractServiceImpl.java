package lk.travel.travellion.service.transfer;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.travel.travellion.dto.transfercontractdto.TransferContractSearchDTO;
import lk.travel.travellion.dto.transfercontractdto.TransfercontractRequestDTO;
import lk.travel.travellion.dto.transfercontractdto.TransfercontractResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Transfer;
import lk.travel.travellion.entity.Transfercontract;
import lk.travel.travellion.exceptions.ForeignKeyConstraintViolationException;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.TransferRepository;
import lk.travel.travellion.repository.TransfercontractRepository;
import lk.travel.travellion.uitl.numberService.NumberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferContractServiceImpl implements TransferContractService {

    private final TransfercontractRepository transfercontractRepository;
    private final ObjectMapper objectMapper;
    private final NumberService numberService;
    private final TransferRepository transferRepository;
    private final TransferContractRelationshipMappingService transferContractRelationshipMappingService;
    private final SpecificationBuilder specificationBuilder;
    private final TransferContractSearchService transferContractSearchService;

    /**
     * Retrieves all transfer contracts and applies optional filtering based on the provided filters.
     *
     * @param filters a map of filter criteria where the key is the filter attribute name
     *                (e.g., "transfercancellationchargesid", "transferdiscountsid", "transferratesid")
     *                and the value is the corresponding filter value. If null or empty, no filtering is applied.
     * @return a list of TransfercontractResponseDTO objects that match the specified filters.
     */
    @Transactional(readOnly = true)
    @Override
    public List<TransfercontractResponseDTO> getAllTransferContracts(HashMap<String, String> filters) {
        List<Transfercontract> transferContracts = transfercontractRepository.findAll();
        List<TransfercontractResponseDTO> transferContractResponseDTOs = objectMapper.toTransferContractResponseDTOs(transferContracts);

        if (filters == null || filters.isEmpty()) {
            return transferContractResponseDTOs;
        }

        try {
            Specification<Transfercontract> transfercontractSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toTransferContractResponseDTOs(transfercontractRepository.findAll(transfercontractSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Retrieves the generated transfer contract reference number for a given supplier branch number.
     *
     * @param supplierBrNo the supplier branch number for which the transfer contract reference number will be generated
     * @return the generated transfer contract reference number as a String
     */
    @Transactional(readOnly = true)
    @Override
    public String getTransferContractRefNumber(String supplierBrNo) {
        return numberService.generateTransferContractReferenceNumber(supplierBrNo);
    }

    /**
     * Searches for transfer contracts based on the provided search criteria.
     *
     * @param transferContractSearchDTO the data transfer object containing search criteria for transfer contracts
     * @return a list of TransfercontractResponseDTO objects that match the search criteria
     */
    @Transactional(readOnly = true)
    @Override
    public List<TransfercontractResponseDTO> searchTransferContracts(TransferContractSearchDTO transferContractSearchDTO) {
        return transferContractSearchService.searchTransferContract(transferContractSearchDTO);
    }

    /**
     * Saves a new transfer contract in the database. Validates the transfer contract reference
     * to ensure no duplicate contracts are created.
     *
     * @param transfercontractRequestDTO the data transfer object containing the details
     *                                   of the transfer contract to be created
     * @return the persisted Transfercontract entity after being successfully saved in the database
     * @throws ResourceAlreadyExistException if a transfer contract with the specified reference
     *                                       already exists
     * @throws TransactionRollbackException if there is a failure during the database operation
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Transfercontract saveTransferContract(TransfercontractRequestDTO transfercontractRequestDTO) {

        if (transfercontractRepository.existsByReference(transfercontractRequestDTO.getReference())) {
            throw new ResourceAlreadyExistException("Transfer contract with reference " + transfercontractRequestDTO.getReference() + " already exists");
        }

        try {
            Transfercontract transferContractEntity = objectMapper.toTransferContractEntity(transfercontractRequestDTO);
            transferContractRelationshipMappingService.setTransferContractEntityRelations(transferContractEntity);
            return transfercontractRepository.save(transferContractEntity);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving Transfer Contract Data", e);
        }
    }

    /**
     * Saves a list of transfer contracts to the database. Each transfer contract is checked for
     * existence by its reference before being saved. If a transfer contract already exists, a
     * {@link ResourceAlreadyExistException} is thrown. If an unexpected error occurs during
     * processing, a {@link TransactionRollbackException} is thrown and the transaction is rolled back.
     *
     * @param transfercontractRequestDTOs the list of transfer contract request DTOs to be saved
     * @throws ResourceAlreadyExistException if a transfer contract with the same reference already exists
     * @throws TransactionRollbackException if an error occurs while processing the transfer contracts
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public void saveTransferContract(List<TransfercontractRequestDTO> transfercontractRequestDTOs) {

        try {
            for (TransfercontractRequestDTO transfercontractRequestDTO : transfercontractRequestDTOs) {
                if (transfercontractRepository.existsByReference(transfercontractRequestDTO.getReference())) {
                    throw new ResourceAlreadyExistException("Transfer contract with reference " + transfercontractRequestDTO.getReference() + " already exists");
                }
                Transfercontract transferContractEntity = objectMapper.toTransferContractEntity(transfercontractRequestDTO);
                transferContractRelationshipMappingService.setTransferContractEntityRelations(transferContractEntity);
                transfercontractRepository.save(transferContractEntity);
            }

        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving Transfer Contract Data", e);
        }
    }

    /**
     * Updates an existing transfer contract based on the provided data transfer object (DTO).
     * This method will validate the existence of the transfer contract by its ID and
     * check for duplicate references before performing the update. It ensures that
     * relationships and properties in the entity are properly updated and saved.
     *
     * @param transfercontractRequestDTO the data transfer object containing the new data for updating the transfer contract
     * @return the updated transfer contract entity
     * @throws ResourceNotFoundException if no transfer contract is found with the provided ID
     * @throws ResourceAlreadyExistException if a different transfer contract with the same reference already exists
     * @throws TransactionRollbackException if any database operation fails during the update process
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Transfercontract updateTransferContract(TransfercontractRequestDTO transfercontractRequestDTO) {

        Transfercontract existingTransferContract = transfercontractRepository.findById(transfercontractRequestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Transfer contract with id " + transfercontractRequestDTO.getId() + " not found"));

        if (!existingTransferContract.getReference().equals(transfercontractRequestDTO.getReference()) &&
                transfercontractRepository.existsByReferenceAndIdNot(transfercontractRequestDTO.getReference(), existingTransferContract.getId())) {
            throw new ResourceAlreadyExistException("Transfer contract with reference " + transfercontractRequestDTO.getReference() + " already exists");
        }

        try {
            Transfercontract transferContractEntity = objectMapper.toTransferContractEntity(transfercontractRequestDTO);
            transferContractRelationshipMappingService.updateTransferContractEntityRelations(existingTransferContract, transferContractEntity);
            BeanUtils.copyProperties(transferContractEntity, existingTransferContract, "id", "transfercancellationcharges", "transferdiscounts", "transferrates", "transfer", "createdon", "updatedon");

            return transfercontractRepository.save(existingTransferContract);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Updating Transfer Contract Data", e);
        }
    }

    /**
     * Deletes a transfer contract by its unique identifier. This includes removing
     * the association with the related transfer, deleting the transfer contract,
     * and subsequently deleting the transfer if no other contracts are associated
     * with it.
     *
     * @param id the unique identifier of the transfer contract to be deleted
     * @throws ResourceNotFoundException if the transfer contract with the specified id
     *         is not found
     */
    @Override
    public void deleteTransferContract(Integer id) {
        try {
            Transfercontract transfercontract = transfercontractRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Transfer Contract with id " + id + " not found"));
            Transfer transfer = transfercontract.getTransfer();
            transfer.getTransfercontracts().remove(transfercontract);
            transfercontract.setTransfer(null);
            transfercontractRepository.deleteById(id);
            transferRepository.deleteById(transfer.getId());
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("Cannot delete transfer contract: Caused by having multiple associations");
        }
    }

    /**
     * Deletes all transfer contracts from the database.
     *
     * The method retrieves all transfer contracts from the repository, removes their associations
     * with the corresponding transfers, and then deletes all the transfer contracts. It ensures
     * proper dissociation of related entities before deletion to maintain data consistency.
     *
     * This method is annotated with @Transactional to ensure that the operations are executed
     * within a single database transaction, maintaining data integrity in case of any failures.
     */
    @Transactional
    @Override
    public void deleteAllContracts() {
        List<Transfercontract> transfercontracts = transfercontractRepository.findAll();

        for (Transfercontract transfercontract : transfercontracts) {
            Transfer transfer = transfercontract.getTransfer();
            if (transfer != null) {
                transfer.getTransfercontracts().remove(transfercontract);
                transfercontract.setTransfer(null);
            }
        }
        transfercontractRepository.deleteAll();
    }

}
