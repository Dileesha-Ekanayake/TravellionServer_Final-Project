package lk.travel.travellion.service.suplierpayment;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentInfoDTO;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentRequestDTO;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Supplierpayment;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.SupplierpaymentRepository;
import lk.travel.travellion.uitl.numberService.NumberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierPaymentServiceIMPL implements SupplierPaymentService{
    
    private final SupplierpaymentRepository supplierpaymentRepository;
    private final ObjectMapper objectMapper;
    private final SpecificationBuilder specificationBuilder;
    private final NumberService numberService;
    private final SupplierPaymentInfoService supplierPaymentInfoService;
    
    /**
     * Retrieves a list of all supplier payments, optionally filtered by the provided criteria.
     *
     * @param filters a map of filter criteria to apply when retrieving supplier payments. If null or empty, no filtering is applied.
     *                Supported keys depend on the implementation of the specification builder.
     * @return a list of {@code SupplierpaymentResponseDTO} objects representing the supplier payments that match the provided filters.
     *         If no filters are provided, returns all supplier payments.
     * @throws ResourceNotFoundException if an invalid filter key is provided.
     */
    @Transactional(readOnly = true)
    @Override
    public List<SupplierpaymentResponseDTO> getAllSupplierPayments(HashMap<String, String> filters) {
        List<Supplierpayment> supplierpayments = supplierpaymentRepository.findAll();
        List<SupplierpaymentResponseDTO> supplierpaymentResponseDTOS = objectMapper.toSupplierpaymentResponseDTOs(supplierpayments);

        if (filters == null || filters.isEmpty()) {
            return supplierpaymentResponseDTOS;
        }

        try {
            Specification<Supplierpayment> supplierpaymentSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toSupplierpaymentResponseDTOs(supplierpaymentRepository.findAll(supplierpaymentSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Generates a unique supplier payment code for the given supplier.
     *
     * @param supplierCode The unique identifier of the supplier for which the payment code needs to be generated.
     * @return A string representing the generated supplier payment code.
     */
    @Override
    public String getSupplierPaymentCode(String supplierCode) {
        return numberService.generateSupplierPaymentCode(supplierCode);
    }

    /**
     * Retrieves payment information for a specific supplier identified by their supplier code.
     *
     * @param supplierCode The unique identifier or code of the supplier whose payment information is to be retrieved.
     * @return A {@link SupplierpaymentInfoDTO} containing the supplier's payment details,
     *         including total amount to be paid, previously paid amount, and balance.
     */
    @Override
    public SupplierpaymentInfoDTO getSupplierPaymentInfo(String supplierCode) {
        return supplierPaymentInfoService.getSupplierPaymentDetails(supplierCode);
    }

    /**
     * Saves a SupplierPayment entity based on the provided SupplierpaymentRequestDTO.
     * If a SupplierPayment with the same code already exists, a {@code ResourceAlreadyExistException} is thrown.
     * Ensures appropriate relationships are set before persisting the entity.
     *
     * @param supplierpaymentRequestDTO the data transfer object containing details of the SupplierPayment to be saved
     *                                  such as code, amount, and supplier information.
     * @return the saved {@link Supplierpayment} entity containing the database persisted data.
     * @throws ResourceAlreadyExistException if a SupplierPayment with the provided code already exists.
     * @throws TransactionRollbackException if any exception occurs during the database operation.
     */
    @Override
    public Supplierpayment saveSupplierPayment(SupplierpaymentRequestDTO supplierpaymentRequestDTO) {
        if (supplierpaymentRepository.existsByCode(((supplierpaymentRequestDTO.getCode())))){
            throw new ResourceAlreadyExistException("Supplierpayment with code " + supplierpaymentRequestDTO.getCode() + " already exist");
        }

        try {
            Supplierpayment supplierPaymentEntity = objectMapper.toSupplierpaymentEntity(supplierpaymentRequestDTO);
            setSupplierPaymentRelationship(supplierPaymentEntity);
            return supplierpaymentRepository.save(supplierPaymentEntity);

        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving CustomerPayment Data", e);
        }

    }

    /**
     * Updates an existing supplier payment record using the provided data.
     *
     * @param supplierpaymentRequestDTO the data transfer object containing the updated details
     *                                   of the supplier payment. It must include any necessary
     *                                   fields required for updating the supplier payment.
     * @return the updated {@code Supplierpayment} entity after the changes are saved to the database.
     */
    @Override
    public Supplierpayment updateSupplierPayment(SupplierpaymentRequestDTO supplierpaymentRequestDTO) {
        return null;
    }

    /**
     * Deletes the SupplierPayment with the specified ID. If the SupplierPayment does not exist,
     * a {@code ResourceAlreadyExistException} is thrown.
     *
     * @param id the ID of the SupplierPayment to be deleted
     *           (must correspond to an existing SupplierPayment record).
     * @throws ResourceAlreadyExistException if the SupplierPayment with the provided ID does not exist
     */
    @Override
    public void deleteSupplierPayment(Integer id) {
        supplierpaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceAlreadyExistException("Supplierpayment with id " + id + " does not exist"));
        supplierpaymentRepository.deleteById(id);
    }

    /**
     * Establishes the relationship between a Supplierpayment entity and its associated SupplierpaymentItems.
     * The method assigns the provided Supplierpayment entity to each SupplierpaymentItem in its list of items.
     *
     * @param supplierpaymentEntity the Supplierpayment entity whose relationship
     *                              with SupplierpaymentItems is to be set.
     *                              This entity contains a list of SupplierpaymentItems.
     */
    private void setSupplierPaymentRelationship(Supplierpayment supplierpaymentEntity) {
        Optional.ofNullable(supplierpaymentEntity.getSupplierpaymentitems())
                .ifPresent(supplierpaymentitems -> {
                    supplierpaymentitems.forEach(supplierpaymentitem -> {
                        supplierpaymentitem.setSupplierpayment(supplierpaymentEntity);
                    });
                });
    }
}
