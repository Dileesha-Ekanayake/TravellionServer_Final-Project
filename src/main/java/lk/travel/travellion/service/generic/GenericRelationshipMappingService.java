package lk.travel.travellion.service.generic;

import lk.travel.travellion.entity.Generic;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenericRelationshipMappingService {

    /**
     * Establishes the associations between the provided Generic entity and its related entities
     * such as discounts, cancellation charges, and rates.
     * Ensures that each related entity properly references the given Generic entity.
     *
     * @param genericEntity the Generic entity whose relationships with discounts,
     *                      cancellation charges, and rates are to be set
     */
    protected void setGenericEntityRelations(Generic genericEntity) {
        Optional.ofNullable(genericEntity.getGenericdiscounts())
                .ifPresent(discounts -> discounts
                        .forEach(discount -> discount.setGeneric(genericEntity)));

        Optional.ofNullable(genericEntity.getGenericcancellationcharges())
                .ifPresent(cancellationcharges -> cancellationcharges
                        .forEach(charge -> charge.setGeneric(genericEntity)));

        Optional.ofNullable(genericEntity.getGenericrates())
                .ifPresent(genericrates -> genericrates
                        .forEach(genericrate -> genericrate.setGeneric(genericEntity)));
    }

    /**
     * Updates the relationships of the existing generic entity with the attributes
     * of the generic entity passed as a parameter. It clears existing relations
     * and establishes new ones based on the provided entity data.
     *
     * @param existingGeneric the existing Generic entity whose relationships are to be updated
     * @param genericEntity the new Generic entity containing updated relationship data
     */
    protected void updateGenericEntityRelations(Generic existingGeneric, Generic genericEntity) {
        existingGeneric.getGenericcancellationcharges().clear();
        Optional.ofNullable(genericEntity.getGenericcancellationcharges())
                .ifPresent(newGenericcancellationcharges -> newGenericcancellationcharges
                        .forEach(charge -> {
                            charge.setGeneric(existingGeneric);
                            existingGeneric.getGenericcancellationcharges().add(charge);
                        }));

        existingGeneric.getGenericdiscounts().clear();
        Optional.ofNullable(genericEntity.getGenericdiscounts())
                .ifPresent(newGenericdiscounts -> newGenericdiscounts
                        .forEach(discount -> {
                            discount.setGeneric(existingGeneric);
                            existingGeneric.getGenericdiscounts().add(discount);
                        }));

        existingGeneric.getGenericrates().clear();
        Optional.ofNullable(genericEntity.getGenericrates())
                .ifPresent(newGenericrates -> newGenericrates
                        .forEach(newRate -> {
                            newRate.setGeneric(existingGeneric);
                            existingGeneric.getGenericrates().add(newRate);
                        }));
    }
}
