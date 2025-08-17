package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.bookingdto.*;
import lk.travel.travellion.entity.Booking;
import lk.travel.travellion.service.booking.BookingService;
import lk.travel.travellion.service.booking.RoomCountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<BookingResponseDTO>>> getBooking(@RequestParam(required = false) HashMap<String, String> filters) {
        List<BookingResponseDTO> bookings = bookingService.getAllBookings(filters);
        return ApiResponse.successResponse(bookings);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<BookingListDTO>>> getBookingList() {
        List<BookingListDTO> bookings = bookingService.getAllBookingList();
        return ApiResponse.successResponse(bookings);
    }

    @GetMapping(path = "/booked-room-count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<RoomCountDTO>>> getBookingRoomCount(@RequestParam Integer accommId, @RequestParam List<String> roomTypes) {
        List<RoomCountDTO> bookedROomCount = bookingService.getAllBookingRoomsCountByAccommodationIdAndRoomTypesList(accommId, roomTypes);
        return ApiResponse.successResponse(bookedROomCount);
    }

    @GetMapping(path = "/next-booking-code", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getBookingReference() {
        Map<String, String> response = new HashMap<>();
        response.put("bookingCode", bookingService.getBookingCode());
        return ApiResponse.successResponse(response);
    }

    @GetMapping(path = "/booking-balance/{bookingCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<BookingBalanceDTO>> getBookingBalance(@PathVariable String bookingCode) {
        BookingBalanceDTO booking = bookingService.getBookingBalance(bookingCode);
        return ApiResponse.successResponse(booking);
    }

    @GetMapping(path = "/booking-view/{bookingCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<BookingViewResponseDTO>> getBookingView(@PathVariable String bookingCode) {
        BookingViewResponseDTO booking = bookingService.getBookingView(bookingCode);
        return ApiResponse.successResponse(booking);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        Booking savedBooking = bookingService.saveBooking(bookingRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Booking : " + savedBooking.getCode()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        Booking updatedBooking = bookingService.updateBooking(bookingRequestDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Booking : " + updatedBooking.getCode()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Booking ID : " + id
        );
    }
}
