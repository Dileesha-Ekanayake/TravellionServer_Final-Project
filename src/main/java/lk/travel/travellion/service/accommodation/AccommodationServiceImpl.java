package lk.travel.travellion.service.accommodation;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.travel.travellion.dto.accommodationdto.AccommodationRequestDTO;
import lk.travel.travellion.dto.accommodationdto.AccommodationResponseDTO;
import lk.travel.travellion.dto.accommodationdto.AccommodationSearchDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Accommodation;
import lk.travel.travellion.exceptions.ForeignKeyConstraintViolationException;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.AccommodationRepository;
import lk.travel.travellion.service.booking.RoomCountDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final ObjectMapper objectMapper;
    private final NumberService numberService;

    private final SpecificationBuilder specificationBuilder;
    private final AccommodationSearchService accommodationSearchService;
    private final AccommodationRelationshipMappingService accommodationRelationshipMappingService;
//    private static final Logger log = LoggerFactory.getLogger(AccommodationServiceImpl.class);

    /**
     * Retrieves a list of accommodations based on the provided filters. If no filters are provided,
     * all accommodations will be retrieved.
     *
     * @param filters a HashMap containing the filtering criteria where the key represents
     *                the filter field and the value represents the filter value.
     *                Pass an empty map or null to retrieve all accommodations.
     * @return a list of AccommodationResponseDTO containing the details of the filtered or
     *         all accommodations in case of no filters.
     * @throws ResourceNotFoundException if an invalid filter key is provided in the filter map.
     */
    @Transactional(readOnly = true)
    @Override
    public List<AccommodationResponseDTO> getAllAccommodations(HashMap<String, String> filters) {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        List<AccommodationResponseDTO> accommodationResponseDTOS = objectMapper.toAccommodationResponseDTOs(accommodations);

        if (filters == null || filters.isEmpty()) {
            return accommodationResponseDTOS;
        }

        try {
            Specification<Accommodation> accommodationSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toAccommodationResponseDTOs(accommodationRepository.findAll(accommodationSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }

    }

    /**
     * Generates a unique accommodation reference number using the provided supplier business registration number.
     *
     * @param supplierBrNo the supplier's business registration number used to generate the reference number
     * @return a unique accommodation reference number associated with the given supplier business registration number
     */
    @Transactional(readOnly = true)
    @Override
    public String getAccommodationRefNumber(String supplierBrNo) {
        return numberService.generateAccommodationReferenceNumber(supplierBrNo);
    }


    /**
     * Searches for accommodations based on the provided search criteria.
     *
     * @param accommodationSearchDTO the data transfer object containing search filters and criteria
     *                                for fetching accommodations
     * @return a list of {@link AccommodationResponseDTO} containing the details of accommodations
     *         matching the search criteria
     */
    @Transactional(readOnly = true)
    @Override
    public List<AccommodationResponseDTO> searchAccommodations(AccommodationSearchDTO accommodationSearchDTO) {
        return accommodationSearchService.searchAccommodation(accommodationSearchDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoomCountDTO> getAllRoomsCountByAccommodationIdAndRoomTypesList(int accommodationId, List<String> roomTypes) {

        // Clean and validate room types
        List<String> cleanedRoomTypes = roomTypes.stream()
                .map(String::trim)
                .filter(roomType -> !roomType.isEmpty())
                .collect(Collectors.toList());

        if (cleanedRoomTypes.isEmpty()) {
            throw new IllegalArgumentException("No valid room types provided");
        }

        return accommodationRepository.getRoomCountsByAccommodationIdAndRoomTypes(accommodationId, cleanedRoomTypes);
    }

    /**
     * Persists a new accommodation entity to the database based on the provided request data.
     * Throws an exception if an accommodation with the same reference already exists.
     * Rolls back the current transaction in case of any error during the operation.
     *
     * @param accommodationRequestDTO the data transfer object containing details of the accommodation to be saved
     * @return the saved accommodation entity
     * @throws ResourceAlreadyExistException if an accommodation with the given reference already exists
     * @throws TransactionRollbackException if an error occurs during the database operation
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Accommodation saveAccommodation(AccommodationRequestDTO accommodationRequestDTO) {

        if (accommodationRepository.existsByReference(accommodationRequestDTO.getReference())) {
            throw new ResourceAlreadyExistException("Accommodation with reference: " + accommodationRequestDTO.getReference() + " already exists");
        }

        try {

            Accommodation accommodationEntity = objectMapper.toAccommodationEntity(accommodationRequestDTO);

            accommodationRelationshipMappingService.setAccommodationEntityRelations(accommodationEntity);

            return accommodationRepository.save(accommodationEntity);

        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving Accommodation Data", e);
        }

    }

    /**
     * Updates an existing accommodation in the system with the details provided in the request DTO.
     * The method ensures that the accommodation exists and handles cases of conflicting references.
     * In case of failure, a rollback will be initiated.
     *
     * @param accommodationRequestDTO the DTO containing the updated details of the accommodation
     *                                 including its ID, reference, and other properties.
     * @return the updated {@link Accommodation} entity after successful persistence in the database.
     * @throws ResourceNotFoundException if no accommodation is found with the provided ID.
     * @throws ResourceAlreadyExistException if another accommodation exists with the same reference.
     * @throws TransactionRollbackException if a database operation error occurs, leading to transaction failure.
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Accommodation updateAccommodation(AccommodationRequestDTO accommodationRequestDTO) {

        Accommodation existingAccommodation = accommodationRepository.findById(accommodationRequestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Accommodation with id: " + accommodationRequestDTO.getId() + " not found"));

        if (!existingAccommodation.getReference().equals(accommodationRequestDTO.getReference()) &&
                accommodationRepository.existsByReferenceAndIdNot(accommodationRequestDTO.getReference(), accommodationRequestDTO.getId())) {
            throw new ResourceAlreadyExistException("Accommodation with reference: " + accommodationRequestDTO.getReference() + " already exists");
        }

        try {

            Accommodation accommodationEntity = objectMapper.toAccommodationEntity(accommodationRequestDTO);
            accommodationRelationshipMappingService.updateAccommodationRelations(existingAccommodation, accommodationEntity);
            BeanUtils.copyProperties(accommodationEntity, existingAccommodation, "id", "accommodationrooms", "accommodationdiscounts", "accommodationcncelationcharges", "createdon", "updatedon");
            return accommodationRepository.save(existingAccommodation);

        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Updating Accommodation Data", e);
        }
    }

    /**
     * Deletes an accommodation by its identifier.
     *
     * @param id the unique identifier of the accommodation to be deleted
     * @throws ResourceNotFoundException if no accommodation with the given id is found
     */
    @Override
    public void deleteAccommodation(Integer id) {

        try {
            accommodationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Accommodation with id: " + id + " not found"));
            accommodationRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("Cannot delete accommodation: Caused by having multiple associations");
        }
    }
}
