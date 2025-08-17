package lk.travel.travellion.service.customer;

import lk.travel.travellion.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerRelationshipMapping {

    /**
     * Establishes relationships between the given Customer entity and its related entities.
     * This method ensures that each related entity (CustomerIdentity, CustomerContact, Passenger)
     * references the provided Customer entity.
     *
     * @param customerEntity the Customer entity whose relationships are to be established.
     *                       Must not be null and should properly initialize its related entities.
     */
    protected void setCustomerRelations(Customer customerEntity) {

        Optional.ofNullable(customerEntity.getCustomerIdentities())
                .ifPresent(identities ->
                        identities.forEach(identity ->
                                identity.setCustomer(customerEntity)));

        Optional.ofNullable(customerEntity.getCustomerContacts())
                .ifPresent(contacts ->
                        contacts.forEach(contact ->
                                contact.setCustomer(customerEntity)));

        Optional.ofNullable(customerEntity.getPassengers())
                .ifPresent(passengers ->
                        passengers.forEach(passenger ->
                                passenger.setCustomer(customerEntity)));
    }

    /**
     * Updates the customer relationships (identities, contacts, and passengers) of an existing customer entity
     * based on the data provided in a new customer entity. This method clears the existing associations
     * in the existing customer entity and populates them with the updated relationships from the new customer entity.
     *
     * @param existingCustomerEntity the existing customer entity whose relationships are to be updated
     * @param customerEntity the new customer entity containing the updated relationship data
     */
    protected void updateCustomerRelations(Customer existingCustomerEntity, Customer customerEntity){

        existingCustomerEntity.getCustomerIdentities().clear();
        Optional.ofNullable(customerEntity.getCustomerIdentities())
                .ifPresent(newIdentities ->
                        newIdentities.forEach(identity -> {
                            identity.setCustomer(existingCustomerEntity);
                            existingCustomerEntity.getCustomerIdentities().add(identity);
                        }));

        existingCustomerEntity.getCustomerContacts().clear();
        Optional.ofNullable(customerEntity.getCustomerContacts())
                .ifPresent(newContacts ->
                        newContacts.forEach(contact -> {
                            contact.setCustomer(existingCustomerEntity);
                            existingCustomerEntity.getCustomerContacts().add(contact);
                        }));

        existingCustomerEntity.getPassengers().clear();
        Optional.ofNullable(customerEntity.getPassengers())
                .ifPresent(newPassengers -> newPassengers.forEach(passenger -> {
                    passenger.setCustomer(existingCustomerEntity);
                    existingCustomerEntity.getPassengers().add(passenger);
                }));
    }
}
