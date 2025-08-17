package lk.travel.travellion.service.tour;

import lk.travel.travellion.entity.Tourcontract;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourContractRelationShipMappingService {

    /**
     * Updates the relationships between a given Tourcontract entity and its associated entities such as
     * tour accommodations, tour transfer contracts, tour occupancies, and tour generics.
     * Ensures that the correct parent-child relationship is established by setting the parent
     * Tourcontract entity on each associated entity.
     *
     * @param tourContractEntity the Tourcontract entity for which relationships are to be established
     *                           with its associated entities
     */
    protected void setTourContractEntityRelations(Tourcontract tourContractEntity) {
        System.out.println(tourContractEntity);
        Optional.ofNullable(tourContractEntity.getTouraccommodations())
                .ifPresent(touraccommodations ->
                        touraccommodations.forEach(touraccommodation -> {
                            touraccommodation.setTourcontract(tourContractEntity);

                            Optional.ofNullable(touraccommodation.getTouraccommodationrooms())
                                    .ifPresent(rooms ->
                                            rooms.forEach(touraccommodationroom ->
                                                    touraccommodationroom.setTouraccommodation(touraccommodation)
                                            )
                                    );
                        })
                );

        Optional.ofNullable(tourContractEntity.getTourtransfercontracts())
                .ifPresent(tourtransfercontracts ->
                        tourtransfercontracts.forEach(tourtransfercontract ->
                                tourtransfercontract.setTourcontract(tourContractEntity)
                        ));
        Optional.ofNullable(tourContractEntity.getTouroccupancies())
                .ifPresent(tourocupancies ->
                        tourocupancies.forEach(tourocupancy ->
                                tourocupancy.setTourcontract(tourContractEntity)
                        ));
        Optional.ofNullable(tourContractEntity.getTourgenerics())
                .ifPresent(tourgenerics ->
                        tourgenerics.forEach(tourgeneric ->
                                tourgeneric.setTourcontract(tourContractEntity)
                        ));
    }

    /**
     * Updates the relationships between an existing Tourcontract entity and the provided Tourcontract entity.
     * Clears the existing relationships in the provided fields of the existing Tourcontract and replaces
     * them with the relationships from the provided Tourcontract entity.
     *
     * @param existingTourContract the existing Tourcontract entity whose relationships are to be updated
     * @param tourContractEntity the Tourcontract entity containing the new relationships to be set
     */
    protected void updateTourContractEntityRelations(Tourcontract existingTourContract, Tourcontract tourContractEntity) {
        existingTourContract.getTouraccommodations().clear();
        Optional.ofNullable(tourContractEntity.getTouraccommodations())
                .ifPresent(newTouraccommodations ->
                        newTouraccommodations.forEach(touraccommodation -> {
                                    touraccommodation.setTourcontract(existingTourContract);
                                    existingTourContract.getTouraccommodations().add(touraccommodation);

                                    Optional.ofNullable(touraccommodation.getTouraccommodationrooms())
                                            .ifPresent(rooms ->
                                                    rooms.forEach(touraccommodationroom ->
                                                            touraccommodationroom.setTouraccommodation(touraccommodation)
                                                    )
                                            );
                                }
                        ));

        existingTourContract.getTourtransfercontracts().clear();
        Optional.ofNullable(tourContractEntity.getTourtransfercontracts())
                .ifPresent(newTourtransfercontracts ->
                        newTourtransfercontracts.forEach(tourtransfercontract -> {
                                    tourtransfercontract.setTourcontract(existingTourContract);
                                    existingTourContract.getTourtransfercontracts().add(tourtransfercontract);
                                }
                        ));

        existingTourContract.getTouroccupancies().clear();
        Optional.ofNullable(tourContractEntity.getTouroccupancies())
                .ifPresent(newTourccupancies ->
                        newTourccupancies.forEach(tourccupancy -> {
                                    tourccupancy.setTourcontract(existingTourContract);
                                    existingTourContract.getTouroccupancies().add(tourccupancy);
                                }
                        ));

        existingTourContract.getTourgenerics().clear();
        Optional.ofNullable(tourContractEntity.getTourgenerics())
                .ifPresent(newTourgenerics ->
                        newTourgenerics.forEach(tourgeneric -> {
                                    tourgeneric.setTourcontract(existingTourContract);
                                    existingTourContract.getTourgenerics().add(tourgeneric);
                                }
                        ));
    }
}
