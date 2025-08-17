package lk.travel.travellion.service.supplier;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.dileesha.jpavalidator.DuplicateValidator;
import lk.travel.travellion.dto.supplierdto.SupplierListDTO;
import lk.travel.travellion.dto.supplierdto.SupplierRequestDTO;
import lk.travel.travellion.dto.supplierdto.SupplierResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Supplier;
import lk.travel.travellion.exceptions.ForeignKeyConstraintViolationException;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.SupplierRepository;
import lk.travel.travellion.uitl.numberService.NumberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ObjectMapper objectMapper;
    private final NumberService numberService;

    private final SpecificationBuilder specificationBuilder;
    private final DuplicateValidator duplicateValidator;

    /**
     * Retrieves a list of all suppliers, optionally filtered based on the given criteria.
     *
     * @param filters a HashMap containing the filtering criteria as key-value pairs.
     *                Supported filters include "userid" to filter by a specific user's ID.
     *                If null or empty, no filters are applied.
     * @return a List of SupplierResponseDTO objects that represent the suppliers, filtered based on the provided criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public List<SupplierResponseDTO> getAllSuppliers(HashMap<String, String> filters) {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierResponseDTO> supplierDTOS = objectMapper.toSupplierResponseDTOs(suppliers);

        if (filters == null || filters.isEmpty()) {
            return supplierDTOS;
        }

        try {
            Specification<Supplier> supplierSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toSupplierResponseDTOs(supplierRepository.findAll(supplierSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all active suppliers with a specific status.
     *
     * @return a list of SupplierListDTO containing details of active suppliers with status ID 1
     */
    @Transactional(readOnly = true)
    @Override
    public List<SupplierListDTO> getAllSupplierList(){
        List<Supplier> suppliers = supplierRepository.findByIsActiveTrueAndSupplierstatus_Id(1);
        return objectMapper.toSupplierListDTOs(suppliers);
    }

    /**
     * Retrieves a list of all active accommodation suppliers with a specific supplier status and type.
     *
     * @return a list of SupplierListDTO objects representing active accommodation suppliers.
     */
    @Transactional(readOnly = true)
    @Override
    public List<SupplierListDTO> getAllAccommSupplierList() {
        List<Supplier> suppliers = supplierRepository.findByIsActiveTrueAndSupplierstatus_IdAndSuppliertype_Id(1,1);
        return objectMapper.toSupplierListDTOs(suppliers);
    }

    /**
     * Retrieves a list of all active suppliers with specific status and type.
     *
     * @return a list of SupplierListDTO objects representing active suppliers
     *         filtered by a predefined status and type.
     */
    @Transactional(readOnly = true)
    @Override
    public List<SupplierListDTO> getAllGenericSupplierList() {
        List<Supplier> suppliers = supplierRepository.findByIsActiveTrueAndSupplierstatus_IdAndSuppliertype_Id(1,3);
        return objectMapper.toSupplierListDTOs(suppliers);
    }

    /**
     * Retrieves a list of all transfer suppliers that are active and meet the specified supplier status
     * and supplier type criteria (status ID = 1 and type ID = 2).
     *
     * @return a list of SupplierListDTO objects representing the transfer suppliers that match the criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public List<SupplierListDTO> getAllTransferSupplierList() {
        List<Supplier> suppliers = supplierRepository.findByIsActiveTrueAndSupplierstatus_IdAndSuppliertype_Id(1,2);
        return objectMapper.toSupplierListDTOs(suppliers);
    }

    /**
     * Generates and retrieves a new Supplier Business Registration Number (BRNo).
     *
     * @return A string representing the generated Supplier BRNo.
     */
    @Transactional(readOnly = true)
    @Override
    public String getSupplierBrNo() {
        return numberService.generateSupplierBrNumber();
    }

    /**
     * Saves a new supplier based on the provided {@link SupplierRequestDTO}.
     * Validates for duplicate supplier attributes (e.g., BRNo, Bank Account, Mobile, etc.)
     * before persisting the supplier to the database.
     *
     * @param supplierRequestDTO the data transfer object containing the details of the supplier to be saved
     * @return the saved Supplier entity after successful persistence
     * @throws ResourceAlreadyExistException if a supplier with duplicate details is found
     */
    @Override
    public Supplier saveSupplier(SupplierRequestDTO supplierRequestDTO) {

        List<String> existingValues = checkExisting(supplierRequestDTO, null,true);
        if (!existingValues.isEmpty()) {
            throw new ResourceAlreadyExistException("Supplier with " + String.join(" or ", existingValues) + " already exists");
        }

        return supplierRepository.save(objectMapper.toSupplierEntity(supplierRequestDTO));
    }

    /**
     * Updates an existing supplier's details based on the provided SupplierRequestDTO.
     * Performs validation to ensure the supplier exists and checks for conflicting data.
     * Throws exceptions if the supplier is not found or if conflicting supplier data already exists.
     *
     * @param supplierRequestDTO the data transfer object containing updated supplier details
     * @return the updated supplier entity after successful modification
     * @throws ResourceNotFoundException if no supplier is found with the specified business registration number (BRNo)
     * @throws ResourceAlreadyExistException if the updated supplier details conflict with existing supplier records
     */
    @Override
    public Supplier updateSupplier(SupplierRequestDTO supplierRequestDTO) {

        // Check for existing supplier (number)
        Supplier existingSupplier = supplierRepository.findByBrno(supplierRequestDTO.getBrno())
                .orElseThrow(() -> new ResourceNotFoundException("Supplier with number " + supplierRequestDTO.getBrno() + " not found"));

        List<String> existingValues = checkExisting(supplierRequestDTO, existingSupplier.getId(),true);

        Supplier updatedSupplier = objectMapper.toSupplierEntity(supplierRequestDTO);

        if (!supplierRequestDTO.getSupplierstatus().getName().equals("Active")) {
            updatedSupplier.setActive(false);
        }

        if (!existingValues.isEmpty()) {
            throw new ResourceAlreadyExistException("Supplier with " + String.join(" or ", existingValues) + " already exists");
        }

        return supplierRepository.save(updatedSupplier);
    }

    /**
     * Deletes a supplier identified by the given ID. If the supplier does not exist, a
     * {@code ResourceNotFoundException} is thrown.
     *
     * @param id the ID of the supplier to be deleted
     * @throws ResourceNotFoundException if a supplier with the specified ID is not found
     */
    @Override
    public void deleteSupplier(Integer id) {

        try {
            supplierRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Supplier with id " + id + " not found")
            );
            supplierRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("Cannot delete supplier: Caused by having multiple associations");
        }
    }

    private List<String> checkExisting(SupplierRequestDTO supplierRequestDTO, Integer currentId, boolean includeValue) {
        Map<String, String> fieldsToValidate = new HashMap<>();
        fieldsToValidate.put("brno", supplierRequestDTO.getBrno());
        fieldsToValidate.put("bank_account", supplierRequestDTO.getBankAccount());
        fieldsToValidate.put("mobile", supplierRequestDTO.getMobile());
        fieldsToValidate.put("land", supplierRequestDTO.getLand());
        fieldsToValidate.put("email", supplierRequestDTO.getEmail());

        return duplicateValidator.checkDuplicates(Supplier.class, fieldsToValidate, "id", currentId, includeValue);
    }
}
