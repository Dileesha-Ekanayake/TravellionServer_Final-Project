package lk.travel.travellion.service.booking;

import lk.travel.travellion.dto.bookingdto.*;
import lk.travel.travellion.entity.Booking;

import java.util.HashMap;
import java.util.List;

public interface BookingService {

    List<BookingResponseDTO> getAllBookings(HashMap<String, String> filters);

    List<BookingListDTO> getAllBookingList();

    List<RoomCountDTO> getAllBookingRoomsCountByAccommodationIdAndRoomTypesList(int accommodationId, List<String> roomTypes);

    BookingBalanceDTO getBookingBalance(String bookingCode);

    BookingViewResponseDTO getBookingView(String bookingCode);

    String getBookingCode();

    Booking saveBooking(BookingRequestDTO bookingRequestDTO);

    Booking updateBooking(BookingRequestDTO bookingRequestDTO);

    void deleteBooking(Integer id);
}
