package lk.travel.travellion.service.booking;

import lk.travel.travellion.entity.Booking;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingRelationshipMappingService {

    /**
     * Establishes the parent-child relationships between the provided booking entity
     * and its associated child entities, such as accommodations, generics, transfers,
     * tours, and passengers. Each child entity is updated to reference the parent booking entity.
     *
     * @param bookingEntity The booking entity whose relationships with its associated
     *                       child entities are to be established.
     */
    protected void setBookingRelationship(Booking bookingEntity) {
        Optional.ofNullable(bookingEntity.getBookingaccommodations())
                .ifPresent(bookingAccommodations -> {
                    bookingAccommodations.forEach(bookingAccommodation -> {
                        bookingAccommodation.setBooking(bookingEntity);

                        Optional.ofNullable(bookingAccommodation.getBookingaccommodationrooms())
                                .ifPresent(bookingaccommodationrooms -> {
                                    bookingaccommodationrooms.forEach(bookingaccommodationroom -> {
                                        bookingaccommodationroom.setBookingaccommodation(bookingAccommodation);
                                    });
                                });
                    });
                });

        Optional.ofNullable(bookingEntity.getBookinggenerics())
                .ifPresent(bookinggenerics -> {
                    bookinggenerics.forEach(bookinggeneric -> {
                        bookinggeneric.setBooking(bookingEntity);

                        Optional.ofNullable(bookinggeneric.getBookinggenericpaxes())
                                .ifPresent(bookinggenericpaxes -> {
                                    bookinggenericpaxes.forEach(bookinggenericpax -> {
                                        bookinggenericpax.setBookinggeneric(bookinggeneric);
                                    });
                                });
                    });
                });

        Optional.ofNullable(bookingEntity.getBookingtransfers())
                .ifPresent(bookingtransfers -> {
                    bookingtransfers.forEach(bookingtransfer -> {
                        bookingtransfer.setBooking(bookingEntity);

                        Optional.ofNullable(bookingtransfer.getBookingtransferdetails())
                                .ifPresent(bookingtransferdetails -> {
                                    bookingtransferdetails.forEach(bookingtransferdetail -> {
                                        bookingtransferdetail.setBookingtransfer(bookingtransfer);
                                    });
                                });
                    });
                });

        Optional.ofNullable(bookingEntity.getBookingtours())
                .ifPresent(bookingtours -> {
                    bookingtours.forEach(bookingtour -> {
                        bookingtour.setBooking(bookingEntity);
                    });
                });

        Optional.ofNullable(bookingEntity.getBookingpassengers())
                .ifPresent(bookingpassengers -> {
                    bookingpassengers.forEach(bookingpassenger -> {
                        bookingpassenger.setBooking(bookingEntity);
                    });
                });


    }

    /**
     * Updates the relationship data of a given existing booking entity by replacing its current related entities
     * with those from a new booking entity. The method clears the relationships in the existing booking entity
     * and establishes new relationships based on the values in the provided booking entity.
     *
     * @param existingBookingEntity the existing booking entity whose relationships are to be updated
     * @param bookingEntity the new booking entity containing updated relationship data
     */
    protected void updateBookingRelationship(Booking existingBookingEntity, Booking bookingEntity) {

        existingBookingEntity.getBookingaccommodations().clear();
        Optional.ofNullable(bookingEntity.getBookingaccommodations())
                .ifPresent(newBookingAccommodations -> {
                    newBookingAccommodations.forEach(newBookingAccommodation -> {
                        newBookingAccommodation.setBooking(existingBookingEntity);
                        existingBookingEntity.getBookingaccommodations().add(newBookingAccommodation);

                        Optional.ofNullable(newBookingAccommodation.getBookingaccommodationrooms())
                                .ifPresent(newBookingAccommodationrooms -> {
                                    newBookingAccommodationrooms.forEach(newBookingAccommodationroom -> {
                                        newBookingAccommodationroom.setBookingaccommodation(newBookingAccommodation);
                                    });
                                });
                    });
                });

        existingBookingEntity.getBookinggenerics().clear();
        Optional.ofNullable(bookingEntity.getBookinggenerics())
                .ifPresent(newBookingGenerics -> {
                    newBookingGenerics.forEach(newBookingGeneric -> {
                        newBookingGeneric.setBooking(existingBookingEntity);
                        existingBookingEntity.getBookinggenerics().add(newBookingGeneric);

                        Optional.ofNullable(newBookingGeneric.getBookinggenericpaxes())
                                .ifPresent(newBookingGenericpaxes -> {
                                    newBookingGenericpaxes.forEach(newBookingGenericpax -> {
                                        newBookingGenericpax.setBookinggeneric(newBookingGeneric);
                                    });
                                });
                    });
                });

        existingBookingEntity.getBookingtransfers().clear();
        Optional.ofNullable(bookingEntity.getBookingtransfers())
                .ifPresent(newBookingTransfers -> {
                    newBookingTransfers.forEach(newBookingTransfer -> {
                        newBookingTransfer.setBooking(existingBookingEntity);
                        existingBookingEntity.getBookingtransfers().add(newBookingTransfer);

                        Optional.ofNullable(newBookingTransfer.getBookingtransferdetails())
                                .ifPresent(newBookingTransferdetails -> {
                                    newBookingTransferdetails.forEach(newBookingTransferdetail -> {
                                        newBookingTransferdetail.setBookingtransfer(newBookingTransfer);
                                    });
                                });
                    });
                });

        existingBookingEntity.getBookingtours().clear();
        Optional.ofNullable(bookingEntity.getBookingtours())
                .ifPresent(newBookingTours -> {
                    newBookingTours.forEach(newBookingTour -> {
                        newBookingTour.setBooking(existingBookingEntity);
                        existingBookingEntity.getBookingtours().add(newBookingTour);
                    });
                });

        existingBookingEntity.getBookingpassengers().clear();
        Optional.ofNullable(bookingEntity.getBookingpassengers())
                .ifPresent(newBookingPassengers -> {
                    newBookingPassengers.forEach(newBookingPassenger -> {
                        newBookingPassenger.setBooking(existingBookingEntity);
                        existingBookingEntity.getBookingpassengers().add(newBookingPassenger);
                    });
                });
    }
}
