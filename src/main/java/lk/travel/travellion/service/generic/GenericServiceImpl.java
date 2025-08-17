package lk.travel.travellion.service.generic;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.travel.travellion.dto.genericdto.GenericRequestDTO;
import lk.travel.travellion.dto.genericdto.GenericResponseDTO;
import lk.travel.travellion.dto.genericdto.GenericSearchDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Generic;
import lk.travel.travellion.exceptions.ForeignKeyConstraintViolationException;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.GenericRepository;
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
public class GenericServiceImpl implements GenericService {

    private final GenericRepository genericRepository;
    private final ObjectMapper objectMapper;
    private final SpecificationBuilder specificationBuilder;
    private final NumberService numberService;
    private final GenericRelationshipMappingService genericRelationshipMappingService;
    private final GenericSearchService genericSearchService;

    /**
     * Retrieves all generic records, optionally filtered based on provided criteria.
     *
     * This method retrieves a list of generic entities from the database, maps them to
     * response DTO objects, and applies optional filtering based on the provided filters map.
     * Supported filter keys include:
     * - "genericratesid" for filtering by generic rates ID.
     * - "genericcancellationchargesid" for filtering by cancellation charges ID.
     * - "genericdiscountsid" for filtering by discount type ID.
     * If no filters are provided or the filters map is empty, the method returns all records.
     *
     * @param filters A map containing key-value pairs for filtering the generic records.
     *                Keys are the filter names, and values are the corresponding filter values.
     *                Can be null or empty for no filtering.
     * @return A list of {@code GenericResponseDTO} objects representing the generic records
     *         after applying any specified filters.
     */
    @Transactional(readOnly = true)
    @Override
    public List<GenericResponseDTO> getAllGenerics(HashMap<String, String> filters) {
        List<Generic> generics = genericRepository.findAll();
        List<GenericResponseDTO> genericResponseDTOs = objectMapper.toGenericResponseDTOs(generics);

        if (filters == null || filters.isEmpty()) {
            return genericResponseDTOs;
        }

        try {
            Specification<Generic> genericSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toGenericResponseDTOs(genericRepository.findAll(genericSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Generates and retrieves a generic reference number using the
     * {@code NumberService.generateGenericReferenceNumber()} method.
     * The reference number is structured to provide a unique and formatted
     * identifier for generics, typically including a prefix, year, and sequence number.
     *
     * @return A string representing the generated generic reference number.
     */
    @Transactional(readOnly = true)
    @Override
    public String getGenericReferenceNumber() {
        return numberService.generateGenericReferenceNumber();
    }

    @Transactional(readOnly = true)
    @Override
    public List<GenericResponseDTO> searchGenerics(GenericSearchDTO genericSearchDTO) {
        return genericSearchService.searchGeneric(genericSearchDTO);
    }

    /**
     * Saves a new generic entity to the database after validating its uniqueness.
     *
     * @param genericRequestDTO the data transfer object containing information to create a new generic entity
     * @return the saved generic entity
     * @throws ResourceAlreadyExistException if a generic with the same reference already exists
     * @throws TransactionRollbackException if a database operation fails and a transaction rollback occurs
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Generic saveGeneric(GenericRequestDTO genericRequestDTO) {

        if (genericRepository.existsByReference(genericRequestDTO.getReference())) {
            throw new ResourceAlreadyExistException("Generic with reference " + genericRequestDTO.getReference() + " already exists");
        }

        try {
            Generic genericEntity = objectMapper.toGenericEntity(genericRequestDTO);
            genericRelationshipMappingService.setGenericEntityRelations(genericEntity);
            return genericRepository.save(genericEntity);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Generic Data", e);
        }
    }

    /**
     * Updates an existing Generic entity with new data provided in the GenericRequestDTO.
     * Validates if the provided reference is unique and performs the update operation.
     * Rolls back the transaction in case of any exceptions.
     *
     * @param genericRequestDTO the data transfer object containing updated information for the Generic entity.
     * @return the updated Generic entity after the successful save operation.
     * @throws ResourceNotFoundException if no Generic entity exists with the provided ID.
     * @throws ResourceAlreadyExistException if the updated reference already exists for another Generic entity.
     * @throws TransactionRollbackException if an error occurs during the database update operation.
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Generic updateGeneric(GenericRequestDTO genericRequestDTO) {

        Generic existingGeneric = genericRepository.findById(genericRequestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Generic with id " + genericRequestDTO.getId() + " not found"));

        if (!existingGeneric.getReference().equals(genericRequestDTO.getReference()) &&
                genericRepository.existsByReferenceAndIdNot(genericRequestDTO.getReference(), existingGeneric.getId())) {
            throw new ResourceAlreadyExistException("Generic with reference " + genericRequestDTO.getReference() + " already exists");
        }

        try {
            Generic genericEntity = objectMapper.toGenericEntity(genericRequestDTO);
            genericRelationshipMappingService.updateGenericEntityRelations(existingGeneric, genericEntity);
            BeanUtils.copyProperties(genericEntity, existingGeneric, "id", "genericcancellationcharges", "genericdiscounts", "genericrates", "createdon", "updatedon");
            return genericRepository.save(genericEntity);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Generic Data", e);
        }
    }

    /**
     * Deletes a Generic record by its unique identifier.
     * If the record does not exist, a {@link ResourceNotFoundException} is thrown.
     *
     * @param id the unique identifier of the Generic record to be deleted
     *           (must not be null).
     */
    @Override
    public void deleteGeneric(Integer id) {
        try {
            genericRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Generic with id " + id + " not found"));

            genericRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("Cannot delete generic: Caused by having multiple associations");
        }
    }
}
