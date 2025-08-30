package lk.travel.travellion.service.booking;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.travel.travellion.dto.bookingdto.*;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Booking;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.BookingRepository;
import lk.travel.travellion.uitl.numberService.NumberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceIMPL implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingRelationshipMappingService bookingRelationshipMappingService;
    private final ObjectMapper objectMapper;
    private final NumberService numberService;

    private final SpecificationBuilder specificationBuilder;

    /**
     * Retrieves a list of booking details, optionally filtered by specified criteria.
     *
     * @param filters a HashMap containing filter criteria as key-value pairs to apply on bookings.
     *                If null or empty, all bookings will be returned without filtering.
     * @return a list of BookingResponseDTO objects containing details of the bookings.
     * @throws ResourceNotFoundException if an invalid filter key is provided in the filters.
     */
    @Transactional(readOnly = true)
    @Override
    public List<BookingResponseDTO> getAllBookings(HashMap<String, String> filters) {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingResponseDTO> bookingResponseDTOS = objectMapper.toBookingResponseDTOs(bookings);

        if (filters == null || filters.isEmpty()) {
            return bookingResponseDTOS;
        }

        try {
            Specification<Booking> bookingSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toBookingResponseDTOs(bookingRepository.findAll(bookingSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all bookings as BookingListDTO objects.
     *
     * @return a list of BookingListDTO containing details of all bookings
     */
    @Transactional(readOnly = true)
    @Override
    public List<BookingListDTO> getAllBookingList() {
        return objectMapper.toBookingListDTOs(bookingRepository.findAll());
    }

    /**
     *
     */
    @Transactional(readOnly = true)
    @Override
    public List<RoomCountDTO> getAllBookingRoomsCountByAccommodationIdAndRoomTypesList(int accommodationId, List<String> roomTypes) {

        // Clean and validate room types
        List<String> cleanedRoomTypes = roomTypes.stream()
                .map(String::trim)
                .filter(roomType -> !roomType.isEmpty())
                .collect(Collectors.toList());

        if (cleanedRoomTypes.isEmpty()) {
            throw new IllegalArgumentException("No valid room types provided");
        }

        return bookingRepository.getAllBookingRoomsCountByAccommodationIdAndRoomTypes(accommodationId, cleanedRoomTypes);
    }

    /**
     * Retrieves the booking balance details for the specified booking code.
     *
     * @param bookingCode the unique code of the booking for which balance details are to be retrieved
     * @return a {@code BookingBalanceDTO} containing the balance details of the specified booking
     * @throws ResourceNotFoundException if no booking is found with the given booking code
     */
    @Transactional(readOnly = true)
    @Override
    public BookingBalanceDTO getBookingBalance(String bookingCode) {
        Booking booking = bookingRepository.findByCode(bookingCode)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with code " + bookingCode + " not found"));

        return objectMapper.toBookingBalanceDTO(booking);
    }

    /**
     * Retrieves booking details for the provided booking code and transforms them into a BookingViewResponseDTO.
     *
     * @param bookingCode the unique identifier of the booking to retrieve
     * @return a BookingViewResponseDTO containing the details of the requested booking
     * @throws ResourceNotFoundException if a booking with the given code is not found
     */
    @Transactional(readOnly = true)
    @Override
    public BookingViewResponseDTO getBookingView(String bookingCode) {
        Booking booking = bookingRepository.findByCode(bookingCode)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with code " + bookingCode + " not found"));

        return objectMapper.toBookingViewResponseDTO(booking);
    }

    /**
     * Generates and retrieves a unique booking code for a new booking.
     *
     * @return a String representing the generated booking code
     */
    @Transactional(readOnly = true)
    @Override
    public String getBookingCode() {
        return numberService.generateBookingCode();
    }

    /**
     * Saves a new booking based on the provided booking request data.
     * If a booking with the same code already exists, a ResourceAlreadyExistException is thrown.
     * This operation is transactional and will roll back in case of a failure.
     *
     * @param bookingRequestDTO the DTO object containing the booking details to be saved
     * @return the saved Booking entity
     * @throws ResourceAlreadyExistException if a booking with the specified code already exists
     * @throws TransactionRollbackException if the database operation fails
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Booking saveBooking(BookingRequestDTO bookingRequestDTO) {

        if (bookingRepository.existsByCode(bookingRequestDTO.getCode())){
            throw new ResourceAlreadyExistException("Booking with code " + bookingRequestDTO.getCode() + " already exist");
        }

        try {

            Booking bookingEntity = objectMapper.toBookingEntity(bookingRequestDTO);
            bookingRelationshipMappingService.setBookingRelationship(bookingEntity);
            return bookingRepository.save(bookingEntity);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving Booking Data", e);
        }

    }

    /**
     * Updates an existing booking with the provided booking details.
     * This operation is transactional and will roll back in case of a failure.
     *
     * @param bookingRequestDTO the DTO object containing the updated booking details, including the unique ID and code
     * @return the updated Booking entity
     * @throws ResourceAlreadyExistException if no booking is found with the given ID or
     *                                       if a booking with the specified code already exists for a different ID
     * @throws TransactionRollbackException if the database operation fails during the update process
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public Booking updateBooking(BookingRequestDTO bookingRequestDTO) {

        Booking existingBooking = bookingRepository.findById(bookingRequestDTO.getId())
                .orElseThrow(() -> new ResourceAlreadyExistException("Booking with id " + bookingRequestDTO.getId() + " does not exist"));

        if (!existingBooking.getCode().equals(bookingRequestDTO.getCode()) &&
                bookingRepository.existsByCodeAndIdNot(bookingRequestDTO.getCode(), existingBooking.getId())){
            throw new ResourceAlreadyExistException("Booking with code " + bookingRequestDTO.getCode() + " already exist");
        }

        try {

            Booking bookingEntity = objectMapper.toBookingEntity(bookingRequestDTO);
            bookingRelationshipMappingService.updateBookingRelationship(existingBooking, bookingEntity);
            BeanUtils.copyProperties(bookingEntity,existingBooking, "id", "bookingaccommodations", "bookinggenerics", "bookingtransfers", "bookingtours", "bookingpassengers", "createdon", "updatedon");
            return bookingRepository.save(existingBooking);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Updating Booking Data", e);
        }
    }

    /**
     * Deletes an existing booking record by its unique identifier.
     *
     * @param id the unique identifier of the booking to be deleted
     * @throws ResourceNotFoundException if a booking with the specified id does not exist
     */
    @Override
    public void deleteBooking(Integer id) {
        bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with id " + id + " does not exist"));
        bookingRepository.deleteById(id);
    }

}
