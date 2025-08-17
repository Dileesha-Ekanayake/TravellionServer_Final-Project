package lk.travel.travellion.service.accommodation;

import lk.travel.travellion.entity.Accommodation;
import lk.travel.travellion.entity.Accommodationroom;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccommodationRelationshipMappingService {

    /**
     * Sets the relationship between an Accommodationroom and its associated Accommodationfacilities.
     * For each Accommodationfacility in the provided Accommodationroom, the associated
     * Accommodationroom is updated.
     *
     * @param accommodationroom the Accommodationroom object containing the associated facilities
     *                          to establish a relationship
     */
    private void setAccommodationRoomInFacilities(Accommodationroom accommodationroom) {
        Optional.ofNullable(accommodationroom.getAccommodationfacilities())
                .ifPresent(accommodationfacilities ->
                        accommodationfacilities.forEach(accommodationfacility ->
                                accommodationfacility.setAccommodationroom(accommodationroom)
                        )
                );
    }

    /**
     * Sets the relationship between an Accommodationroom and its associated rates.
     * For each rate in the Accommodationroom's rates list, this method establishes
     * a connection back to the given Accommodationroom.
     *
     * @param accommodationroom The Accommodationroom entity whose rates need to be updated
     *                          with a reference back to the room entity. It may contain
     *                          a list of associated Accommodationrates.
     */
    private void setAccommodationRoomInRates(Accommodationroom accommodationroom) {
        Optional.ofNullable(accommodationroom.getAccommodationrates())
                .ifPresent(accommodationrates ->
                        accommodationrates.forEach(accommodationrate ->
                                accommodationrate.setAccommodationroom(accommodationroom)
                        )
                );
    }

    /**
     * Associates the given accommodation room with its related occupancies and paxes.
     * Ensures that each occupancy pax within the provided accommodation room
     * has a reference back to the accommodation room set.
     *
     * @param accommodationroom the accommodation room whose occupancies and paxes
     *                          should be linked with the room itself.
     */
    private void setAccommodationRoomInOccupancies(Accommodationroom accommodationroom) {
        Optional.ofNullable(accommodationroom.getAccommodationoccupanciespaxes())
                .ifPresent(accommodationoccupanciespaxes ->
                        accommodationoccupanciespaxes.forEach(accommodationoccupanciespax ->
                                accommodationoccupanciespax.setAccommodationroom(accommodationroom)
                        )
                );
    }

    /**
     * Sets the relationships for the given Accommodation entity.
     * This method establishes the connections between the accommodation entity
     * and its related entities, such as discounts, cancellation charges,
     * rooms, facilities, rates, and occupancies.
     *
     * @param accommodationEntity the Accommodation entity for which relations are to be set
     */
    protected void setAccommodationEntityRelations(Accommodation accommodationEntity) {
        // Set Accommodation relations
        Optional.ofNullable(accommodationEntity.getAccommodationdiscounts()).ifPresent(discounts -> discounts.forEach(discount -> discount.setAccommodation(accommodationEntity)));
        Optional.ofNullable(accommodationEntity.getAccommodationcncelationcharges()).ifPresent(charges -> charges.forEach(charge -> charge.setAccommodation(accommodationEntity)));
        Optional.ofNullable(accommodationEntity.getAccommodationrooms()).ifPresent(rooms -> rooms.forEach(room -> {
            room.setAccommodation(accommodationEntity);
            setAccommodationRoomInFacilities(room);
            setAccommodationRoomInRates(room);
            setAccommodationRoomInOccupancies(room);
        }));
    }

    /**
     * Updates the relations of an existing accommodation with the relations of the provided accommodation entity.
     * This includes accommodation discounts, cancellation charges, and rooms.
     * Old relations on the existing accommodation will be cleared and replaced with the new ones from the provided entity.
     *
     * @param existingAccommodation the existing accommodation object whose relations are to be updated
     * @param accommodationEntity the accommodation entity containing the updated relations
     */
    protected void updateAccommodationRelations(Accommodation existingAccommodation, Accommodation accommodationEntity) {
        // Update Accommodation relations (clear and add new)
        existingAccommodation.getAccommodationdiscounts().clear();
        Optional.ofNullable(accommodationEntity.getAccommodationdiscounts())
                .ifPresent(newDiscounts ->
                        newDiscounts.forEach(discount -> {
                            discount.setAccommodation(existingAccommodation);
                            existingAccommodation.getAccommodationdiscounts().add(discount);
                        }));

        existingAccommodation.getAccommodationcncelationcharges().clear();
        Optional.ofNullable(accommodationEntity.getAccommodationcncelationcharges())
                .ifPresent(newCharges ->
                        newCharges.forEach(charge -> {
                            charge.setAccommodation(existingAccommodation);
                            existingAccommodation.getAccommodationcncelationcharges().add(charge);
                        }));

        existingAccommodation.getAccommodationrooms().clear();
        Optional.ofNullable(accommodationEntity.getAccommodationrooms())
                .ifPresent(newRooms ->
                        newRooms.forEach(room -> {
                            room.setAccommodation(existingAccommodation);
                            setAccommodationRoomInFacilities(room);
                            setAccommodationRoomInRates(room);
                            setAccommodationRoomInOccupancies(room);
                            existingAccommodation.getAccommodationrooms().add(room);
                        }));
    }
}
