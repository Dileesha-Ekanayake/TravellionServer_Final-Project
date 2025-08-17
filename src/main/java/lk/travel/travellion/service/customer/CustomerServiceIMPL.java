package lk.travel.travellion.service.customer;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.dileesha.jpavalidator.DuplicateValidator;
import lk.travel.travellion.dto.customerdto.CustomerListDTO;
import lk.travel.travellion.dto.customerdto.CustomerRequestDTO;
import lk.travel.travellion.dto.customerdto.CustomerResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Customer;
import lk.travel.travellion.entity.CustomerContact;
import lk.travel.travellion.entity.CustomerIdentity;
import lk.travel.travellion.exceptions.ForeignKeyConstraintViolationException;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.CustomerRepository;
import lk.travel.travellion.uitl.numberService.NumberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceIMPL implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;
    private final CustomerRelationshipMapping customerRelationshipMapping;
    private final NumberService numberService;
    private final SpecificationBuilder specificationBuilder;
    private final DuplicateValidator duplicateValidator;


    /**
     *
     */
    @Transactional(readOnly = true)
    @Override
    public List<CustomerResponseDTO> getAllCustomers(HashMap<String, String> filters) {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerResponseDTO> customerDTOs = objectMapper.toCustomerResponseDTOs(customers);

        if (filters == null || filters.isEmpty()) {
            return customerDTOs;
        }

        try {
            Specification<Customer> customerSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toCustomerResponseDTOs(customerRepository.findAll(customerSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all customers mapped to CustomerListDTO objects.
     *
     * @return a list of CustomerListDTO containing details of all customers.
     */
    @Transactional(readOnly = true)
    @Override
    public List<CustomerListDTO> getAllCustomerList() {
        return objectMapper.toCustomerListDTOs(customerRepository.findAll());
    }

    /**
     * Generates and returns a unique customer code.
     * The customer code is generated using the logic implemented in the NumberService.
     *
     * @return a string representing the unique customer code
     */
    @Transactional(readOnly = true)
    @Override
    public String getCustomerCode() {
        return numberService.generateCustomerCode();
    }

    /**
     * Saves a new customer to the database after performing validation checks
     * on the customer's basic, identity, and contact information to ensure
     * the customer does not already exist.
     *
     * @param customerRequestDTO the data transfer object containing details
     *                           of the customer to be saved
     * @return the saved Customer entity if the operation is successful
     * @throws ResourceAlreadyExistException if a conflict is detected with
     *                                       existing customer details
     * @throws TransactionRollbackException if there is a failure during
     *                                       the database operation
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Customer saveCustomer(CustomerRequestDTO customerRequestDTO) {
        List<String> existingValues1 = checkExistingCustomerBasicInfo(customerRequestDTO, null, true);
        List<String> existingValues2 = checkExistingCustomerIdentityInfo(customerRequestDTO, null, true);
        List<String> existingValues3 = checkExistingCustomerContactInfo(customerRequestDTO, null, true);

        List<String> allExistingValues = new ArrayList<>();
        allExistingValues.addAll(existingValues1);
        allExistingValues.addAll(existingValues2);
        allExistingValues.addAll(existingValues3);

        if (!allExistingValues.isEmpty()) {
            throw new ResourceAlreadyExistException("Customer with " + String.join(" or ", allExistingValues) + " already exist");
        }

        try {
            Customer customerEntity = objectMapper.toCustomerEntity(customerRequestDTO);
            customerRelationshipMapping.setCustomerRelations(customerEntity);

            return customerRepository.save(customerEntity);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving Customer Data", e);
        }

    }

    /**
     *
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Customer updateCustomer(CustomerRequestDTO customerRequestDTO) {

        Customer existingCustomer = customerRepository.findByCode(customerRequestDTO.getCode())
                .orElseThrow(() -> new ResourceNotFoundException("Customer with code " + customerRequestDTO.getCode() + " not found"));

        int id = existingCustomer.getCustomerIdentities()
                .stream()
                .findFirst()
                .get()
                .getId();

        List<String> existingFields1 = checkExistingCustomerBasicInfo(customerRequestDTO, existingCustomer.getId(), true);
        List<String> existingFields2 = checkExistingCustomerIdentityInfo(customerRequestDTO, id, true);
        List<String> existingFields3 = checkExistingCustomerContactInfo(customerRequestDTO, id, true);

        List<String> allExistingFields = new ArrayList<>();
        allExistingFields.addAll(existingFields1);
        allExistingFields.addAll(existingFields2);
        allExistingFields.addAll(existingFields3);
        if (!allExistingFields.isEmpty()) {
            throw new ResourceAlreadyExistException("Customer with " + String.join(" or ", allExistingFields) + " already exists");
        }

        try {
            Customer customerEntity = objectMapper.toCustomerEntity(customerRequestDTO);

            customerRelationshipMapping.updateCustomerRelations(existingCustomer, customerEntity);
            BeanUtils.copyProperties(customerEntity, existingCustomer, "id", "customerContatcts", "customerIdentities", "passengers", "createdon", "updatedon");
            return customerRepository.save(existingCustomer);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Updating Customer Data", e);
        }
    }

    /**
     * Deletes a customer with the given ID from the database.
     * If the customer does not exist, a {@link ResourceNotFoundException} is thrown.
     * If the customer cannot be deleted due to associated records,
     * a {@link ForeignKeyConstraintViolationException} is thrown.
     *
     * @param id the ID of the customer to be deleted
     * @throws ResourceNotFoundException              if a customer with the specified ID is not found
     * @throws ForeignKeyConstraintViolationException if the customer cannot be deleted due to foreign key constraints
     */
    @Override
    public void deleteCustomer(Integer id) {

        try {
            Customer existingCustomer = customerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer with id " + id + " not found"));

            customerRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("Cannot delete customer: Caused by having multiple associations");
        }
    }

    /**
     * Validates and checks for duplicates in customer basic information based on specified criteria.
     *
     * @param customerRequestDTO the customer request data transfer object containing customer information to validate
     * @param currentId the current customer ID to exclude from duplicate checks
     * @param includeValue a flag indicating whether to include the value in the validation results
     * @return a list of strings representing duplicate field names if any duplicates are found, or an empty list if no duplicates are found
     */
    private List<String> checkExistingCustomerBasicInfo(CustomerRequestDTO customerRequestDTO, Integer currentId, boolean includeValue) {
        Map<String, String> fieldsToValidate = new HashMap<>();
        fieldsToValidate.put("code", customerRequestDTO.getCode());
        return duplicateValidator.checkDuplicates(Customer.class, fieldsToValidate, "id", currentId, includeValue);
    }

    /**
     * Checks for duplicate customer identity information such as NIC and passport number
     * within the existing customer records.
     *
     * @param customerRequestDTO the object containing customer identity details to be validated
     * @param currentId the unique identifier of the current record to exclude from validation
     * @param includeValue boolean indicating whether to include the found duplicate value in the result
     * @return a list of duplicates found, represented by field names and their corresponding values
     */
    private List<String> checkExistingCustomerIdentityInfo(CustomerRequestDTO customerRequestDTO, Integer currentId, boolean includeValue) {
        Map<String, String> fieldsToValidate = new HashMap<>();

        String nic = customerRequestDTO.getCustomerIdentities().stream()
                .map(identity -> identity.getNic())
                .filter(Objects::nonNull)
                .filter(nicValue -> !nicValue.isEmpty())
                .findFirst()
                .orElse(null);
        String passport = customerRequestDTO.getCustomerIdentities().stream()
                .map(identity -> identity.getPassportNo())
                .filter(Objects::nonNull)
                .filter(nicValue -> !nicValue.isEmpty())
                .findFirst()
                .orElse(null);

        fieldsToValidate.put("nic", nic);
        fieldsToValidate.put("passportNo", passport);
        return duplicateValidator.checkDuplicates(CustomerIdentity.class, fieldsToValidate, "id", currentId, includeValue);
    }

    /**
     * Checks the existing customer contact information for potential duplicates in the system.
     * The method evaluates the provided customer contact details and validates them against existing records.
     *
     * @param customerRequestDTO the DTO object that contains the customer's contact information
     * @param currentId the identifier of the current customer for comparison purposes
     * @param includeValue a flag indicating whether to include the value in the duplicate validation process
     * @return a list of field names or error messages where duplicates are detected
     */
    private List<String> checkExistingCustomerContactInfo(CustomerRequestDTO customerRequestDTO, Integer currentId, boolean includeValue) {
        Map<String, String> fieldsToValidate = new HashMap<>();

        String mobile = customerRequestDTO.getCustomerContacts().stream()
                .map(identity -> identity.getMobile())
                .filter(Objects::nonNull)
                .filter(nicValue -> !nicValue.isEmpty())
                .findFirst()
                .orElse(null);
        String email = customerRequestDTO.getCustomerContacts().stream()
                .map(identity -> identity.getEmail())
                .filter(Objects::nonNull)
                .filter(nicValue -> !nicValue.isEmpty())
                .findFirst()
                .orElse(null);
        String land = customerRequestDTO.getCustomerContacts().stream()
                .map(identity -> identity.getLand())
                .filter(Objects::nonNull)
                .filter(nicValue -> !nicValue.isEmpty())
                .findFirst()
                .orElse(null);

        fieldsToValidate.put("mobile", mobile);
        fieldsToValidate.put("email", email);
        fieldsToValidate.put("land", land);
        return duplicateValidator.checkDuplicates(CustomerContact.class, fieldsToValidate, "id", currentId, includeValue);
    }
}
