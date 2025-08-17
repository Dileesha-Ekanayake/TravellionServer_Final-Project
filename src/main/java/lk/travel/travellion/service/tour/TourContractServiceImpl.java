package lk.travel.travellion.service.tour;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.travel.travellion.dto.tourcontractdto.TourcontractRequestDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractResponseDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractSearchDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractViewResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Tourcontract;
import lk.travel.travellion.exceptions.ForeignKeyConstraintViolationException;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.TourcontractRepository;
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
public class TourContractServiceImpl implements TourContractService {

    private final TourcontractRepository tourcontractRepository;
    private final ObjectMapper objectMapper;
    private final NumberService numberService;
    private final SpecificationBuilder specificationBuilder;
    private final TourSearchService tourSearchService;
    private final TourContractRelationShipMappingService tourContractRelationShipMappingService;

    /**
     * Retrieves all tour contracts, optionally filtered by provided criteria.
     * If no filters are applied, it fetches all available tour contracts.
     *
     * @param filters a map of filter criteria where keys are field names and values are the filter values
     * @return a list of tour contract response DTOs representing the filtered or unfiltered set of tour contracts
     * @throws ResourceNotFoundException if provided filter keys are invalid or do not match any field
     */
    @Transactional(readOnly = true)
    @Override
    public List<TourcontractResponseDTO> getAllTourContracts(HashMap<String, String> filters) {
        List<Tourcontract> tourContracts = tourcontractRepository.findAll();
        List<TourcontractResponseDTO> tourContractResponseDTOS = objectMapper.toTourcontractResponseDTOs(tourContracts);

        if (filters == null || filters.isEmpty()) {
            return tourContractResponseDTOS;
        }

        try {
            Specification<Tourcontract> tourcontractSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toTourcontractResponseDTOs(tourcontractRepository.findAll(tourcontractSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Generates and returns a unique reference number for a tour contract.
     *
     * @return a unique string representing the tour contract reference number
     */
    @Transactional(readOnly = true)
    @Override
    public String getTourContractReference() {
        return numberService.generateTourContractReferenceNumber();
    }

    /**
     * Searches for tours based on the specified search criteria.
     * This method delegates the search operation to the TourSearchService
     * and retrieves a list of tours that match the provided search parameters.
     *
     * @param tourcontractSearchDTO the data transfer object containing the criteria
     *                              for searching tours, such as filters and search
     *                              parameters.
     * @return a list of TourcontractResponseDTO objects representing the tours
     *         that match the search criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public List<TourcontractResponseDTO> searchTours(TourcontractSearchDTO tourcontractSearchDTO) {
        return tourSearchService.searchTour(tourcontractSearchDTO);
    }

    /**
     * Retrieves the view representation of a Tour Contract based on the provided tour reference.
     * This method fetches the Tour Contract entity corresponding to the given reference
     * and converts it into a response DTO for view purposes.
     *
     * @param tourReference the unique reference identifier of the Tour Contract to be viewed
     * @return a TourcontractViewResponseDTO object representing the view details of the Tour Contract
     * @throws ResourceNotFoundException if no Tour Contract is found for the given reference
     */
    @Transactional(readOnly = true)
    @Override
    public TourcontractViewResponseDTO getTourContractView(String tourReference) {
        Tourcontract tourcontract = tourcontractRepository.findByReference(tourReference)
                .orElseThrow(() -> new ResourceNotFoundException("Tour Contract with reference " + tourReference + " not found"));

        return objectMapper.toTourcontractViewResponseDTO(tourcontract);
    }

    /**
     * Saves a new Tour Contract based on the provided request data.
     * Ensures that the Tour Contract reference is unique and sets up
     * any necessary relationships before persisting the entity.
     *
     * @param tourcontractRequestDTO the data transfer object containing the details of the Tour Contract to be saved
     * @return the saved Tour Contract entity
     * @throws ResourceAlreadyExistException if a Tour Contract with the given reference already exists
     * @throws TransactionRollbackException if an error occurs during the database operation
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Tourcontract saveTourContract(TourcontractRequestDTO tourcontractRequestDTO) {

        if (tourcontractRepository.existsByReference(tourcontractRequestDTO.getReference())) {
            throw new ResourceAlreadyExistException("Tour Contract with reference " + tourcontractRequestDTO.getReference() + " already exists");
        }

        try {
            Tourcontract tourContractEntity = objectMapper.toTourcontractEntity(tourcontractRequestDTO);
            tourContractRelationShipMappingService.setTourContractEntityRelations(tourContractEntity);
            return tourcontractRepository.save(tourContractEntity);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving Tour Contract Data", e);
        }
    }

    /**
     * Updates an existing Tourcontract entity based on the provided data transfer object.
     * This method checks for the existence of the Tourcontract, validates unique constraints
     * on the reference field, updates the associated relationships, and saves the updated entity
     * into the database. Transactions are managed to ensure rollback in case of an exception.
     *
     * @param tourcontractRequestDTO the data transfer object containing the updated details
     *                               for the Tourcontract.
     *                               The ID is used to locate the existing record, and other
     *                               fields are used to update the entity.
     * @return the updated Tourcontract entity after successful persistence.
     * @throws ResourceNotFoundException if the Tourcontract with the specified ID does not exist.
     * @throws ResourceAlreadyExistException if another Tourcontract exists with the same reference
     *                                       but a different ID.
     * @throws TransactionRollbackException if there is any database operation failure during
     *                                      the update process.
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Tourcontract updateTourContract(TourcontractRequestDTO tourcontractRequestDTO) {

        Tourcontract existingTourContract = tourcontractRepository.findById(tourcontractRequestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tour Contract with id " + tourcontractRequestDTO.getId() + " not found"));

        if (!existingTourContract.getReference().equals(tourcontractRequestDTO.getReference()) &&
                tourcontractRepository.existsByReferenceAndIdNot(tourcontractRequestDTO.getReference(), existingTourContract.getId())) {
            throw new ResourceAlreadyExistException("Tour Contract with reference " + tourcontractRequestDTO.getReference() + " already exists");
        }

        try {
            Tourcontract tourContractEntity = objectMapper.toTourcontractEntity(tourcontractRequestDTO);
            tourContractRelationShipMappingService.updateTourContractEntityRelations(existingTourContract, tourContractEntity);
            BeanUtils.copyProperties(tourContractEntity, existingTourContract, "id", "touraccommodations", "tourtransfercontracts", "touroccupancies", "tourgenerics", "createdon", "updatedon");

            return tourcontractRepository.save(tourContractEntity);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Updating Tour Contract Data", e);
        }
    }

    /**
     * Deletes a tour contract by its unique identifier.
     *
     * @param id the unique identifier of the tour contract to be deleted
     * @throws ResourceNotFoundException if no tour contract is found with the specified id
     */
    @Override
    public void deleteTourContract(Integer id) {
        try {
            tourcontractRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Tour Contract with id " + id + " not found"));
            tourcontractRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("Cannot delete tour: Caused by having multiple associations");
        }
    }

}
