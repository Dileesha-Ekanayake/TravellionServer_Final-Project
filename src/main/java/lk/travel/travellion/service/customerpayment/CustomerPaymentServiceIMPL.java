package lk.travel.travellion.service.customerpayment;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.travel.travellion.dto.customerpaymentdto.CustomerpaymentRequestDTO;
import lk.travel.travellion.dto.customerpaymentdto.CustomerpaymentResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Customerpayment;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.CustomerpaymentRepository;
import lk.travel.travellion.service.booking.BookingBalanceUpdateService;
import lk.travel.travellion.uitl.numberService.NumberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerPaymentServiceIMPL implements CustomerPaymentService{

    private final CustomerpaymentRepository customerpaymentRepository;
    private final ObjectMapper objectMapper;
    private final SpecificationBuilder specificationBuilder;
    private final CustomerPaymentRelationshipMapping customerPaymentRelationshipMapping;

    private final NumberService numberService;

    private final BookingBalanceUpdateService bookingBalanceUpdateService;

    /**
     * Retrieves all customer payments, optionally applying filter criteria.
     *
     * @param filters a {@code HashMap<String, String>} containing the filter criteria;
     *                if null or empty, no filters will be applied.
     * @return a {@code List<CustomerpaymentResponseDTO>} containing the customer payment details
     *         after applying the specified filters, or all customer payments if no filters are provided.
     * @throws ResourceNotFoundException if an invalid filter key is supplied in the filter criteria.
     */
    @Transactional(readOnly = true)
    @Override
    public List<CustomerpaymentResponseDTO> getAllCustomerPayments(HashMap<String, String> filters) {
        List<Customerpayment> customerpayments = customerpaymentRepository.findAll();
        List<CustomerpaymentResponseDTO> customerpaymentResponseDTOS = objectMapper.toCustomerpaymentResponseDTOs(customerpayments);

        if (filters == null || filters.isEmpty()) {
            return customerpaymentResponseDTOS;
        }

        try {
            Specification<Customerpayment> customerpaymentSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toCustomerpaymentResponseDTOs(customerpaymentRepository.findAll(customerpaymentSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Retrieves a customer payment code for the specified customer.
     * The code is generated based on the customer's unique code and other predefined rules.
     *
     * @param customerCode the unique code identifying the customer for whom the payment code is generated
     * @return a string representing the generated customer payment code
     */
    @Transactional(readOnly = true)
    @Override
    public String getCustomerPaymentCode(String customerCode) {
        return numberService.generateCustomerPaymentCode(customerCode);
    }

    /**
     * Saves a new customer payment record in the database, ensuring that the provided
     * code does not already exist. Establishes relationships between the customer payment
     * entity and other related entities, and updates the booking balance accordingly.
     * Rolls back the transaction if an exception occurs during the save operation.
     *
     * @param customerpaymentRequestDTO the data transfer object containing customer payment details
     * @return the saved Customerpayment entity containing the persisted details
     * @throws ResourceAlreadyExistException if a customer payment with the specified code already exists
     * @throws TransactionRollbackException if the transaction fails and requires rollback
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Customerpayment saveCustomerPayment(CustomerpaymentRequestDTO customerpaymentRequestDTO) {

        if (customerpaymentRepository.existsByCode(customerpaymentRequestDTO.getCode())){
            throw new ResourceAlreadyExistException("CustomerPayment with code " + customerpaymentRequestDTO.getCode() + " already exist");
        }

        try {

            Customerpayment customerPaymentEntity = objectMapper.toCustomerpaymentEntity(customerpaymentRequestDTO);
            customerPaymentRelationshipMapping.setCustomerPaymentRelationship(customerPaymentEntity);
            Customerpayment savedCustomerPayment = customerpaymentRepository.save(customerPaymentEntity);
            bookingBalanceUpdateService.updateBookingBalance(savedCustomerPayment.getBooking().getCode(), savedCustomerPayment.getPaidamount());
            return savedCustomerPayment;

        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving CustomerPayment Data", e);
        }

    }

    /**
     * Updates an existing customer payment record based on the details provided in the request DTO.
     * Ensures that the update operation follows certain validation rules, including checks for
     * existing payment records, duplicate codes, and maintaining relationships with other entities.
     * Rolls back the transaction if an exception occurs during the update process.
     *
     * @param customerpaymentRequestDTO the data transfer object containing updated details of the customer payment
     * @return the updated Customerpayment entity with the persisted changes
     * @throws ResourceNotFoundException if no customer payment is found with the specified ID in the request DTO
     * @throws ResourceAlreadyExistException if a customer payment with the same code already exists under different conditions
     * @throws TransactionRollbackException if updating the customer payment fails or violates transactional conditions
     */
    @Override
    public Customerpayment updateCustomerPayment(CustomerpaymentRequestDTO customerpaymentRequestDTO) {

        int existingCustomerPaymentCount = customerpaymentRepository.countByBookingCode(customerpaymentRequestDTO.getCode());

        if (existingCustomerPaymentCount > 1){
            throw new TransactionRollbackException("Payment can not be update because there are more than one payments for the booking with code " + customerpaymentRequestDTO.getCode() + " in the database. Please contact the system administrator for more details.");
        }

        Customerpayment existingCustomerPayment = customerpaymentRepository.findById(customerpaymentRequestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("CustomerPayment with id " + customerpaymentRequestDTO.getId() + " does not exist"));

        if (!existingCustomerPayment.getCode().equals(customerpaymentRequestDTO.getCode()) &&
                customerpaymentRepository.existsByCodeAndIdNot(customerpaymentRequestDTO.getCode(), existingCustomerPayment.getId())) {
            throw new ResourceAlreadyExistException("CustomerPayment with code " + customerpaymentRequestDTO.getCode() + " already exist");
        }

        try {

            Customerpayment customerPaymentEntity = objectMapper.toCustomerpaymentEntity(customerpaymentRequestDTO);
            customerPaymentRelationshipMapping.updateCustomerPaymentRelationship(existingCustomerPayment, customerPaymentEntity);
            BeanUtils.copyProperties(customerPaymentEntity, existingCustomerPayment , "id", "customerpaymentinformations", "customerpaymentreceipts", "createdon", "updatedon");
            Customerpayment updatedCustomerPayment = customerpaymentRepository.save(existingCustomerPayment);
            bookingBalanceUpdateService.updateExistingPayment(updatedCustomerPayment.getBooking().getCode());
            return updatedCustomerPayment;

        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Updating CustomerPayment Data", e);
        }
    }

    /**
     * Deletes a customer payment by its unique identifier.
     * If no payment is found with the provided identifier, a {@code ResourceAlreadyExistException} is thrown.
     *
     * @param id the unique identifier of the customer payment to be deleted
     *           must not be null
     */
    @Override
    public void deleteCustomerPayment(Integer id) {
        customerpaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceAlreadyExistException("CustomerPayment with id " + id + " does not exist"));
        customerpaymentRepository.deleteById(id);
    }
}
