package lk.travel.travellion.service.customerpayment;

import lk.travel.travellion.entity.Customerpayment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerPaymentRelationshipMapping {


    /**
     * Establishes the relationship between a Customerpayment entity and its related entities:
     * Customerpaymentinformation and Customerpaymentreceipt. Ensures that each related entity
     * is properly associated with the provided Customerpayment entity.
     *
     * @param customerPaymentEntity the Customerpayment entity for which relationships
     *                              with related entities are to be established
     */
    protected void setCustomerPaymentRelationship(Customerpayment customerPaymentEntity) {

        Optional.ofNullable(customerPaymentEntity.getCustomerpaymentinformations())
                .ifPresent(customerpaymentinformations -> {
                    customerpaymentinformations.forEach(customerpaymentinformation -> {
                        customerpaymentinformation.setCustomerpayment(customerPaymentEntity);
                    });
                });

        Optional.ofNullable(customerPaymentEntity.getCustomerpaymentreceipts())
                .ifPresent(customerpaymentreceipts -> {
                    customerpaymentreceipts.forEach(customerpaymentreceipt -> {
                        customerpaymentreceipt.setCustomerpayment(customerPaymentEntity);
                    });
                });
    }

    /**
     * Updates the relationship of the given existing CustomerPayment entity with the provided CustomerPayment entity.
     * Clears the existing relationships for customer payment information and customer payment receipts in the existing entity
     * and establishes new relationships based on the provided entity.
     *
     * @param existingCustomerPayment the existing CustomerPayment entity whose relationships are to be updated
     * @param customerPaymentEntity the CustomerPayment entity containing the new relationship data to be applied
     */
    protected void updateCustomerPaymentRelationship(Customerpayment existingCustomerPayment, Customerpayment customerPaymentEntity) {

        existingCustomerPayment.getCustomerpaymentinformations().clear();
        Optional.ofNullable(customerPaymentEntity.getCustomerpaymentinformations())
                .ifPresent(customerpaymentinformations -> {
                    customerpaymentinformations.forEach(customerpaymentinformation -> {
                        customerpaymentinformation.setCustomerpayment(existingCustomerPayment);
                        existingCustomerPayment.getCustomerpaymentinformations().add(customerpaymentinformation);
                    });
                });

        existingCustomerPayment.getCustomerpaymentreceipts().clear();
        Optional.ofNullable(customerPaymentEntity.getCustomerpaymentreceipts())
                .ifPresent(customerpaymentreceipts -> {
                    customerpaymentreceipts.forEach(customerpaymentreceipt -> {
                        customerpaymentreceipt.setCustomerpayment(existingCustomerPayment);
                        existingCustomerPayment.getCustomerpaymentreceipts().add(customerpaymentreceipt);
                    });
                });
    }
}
