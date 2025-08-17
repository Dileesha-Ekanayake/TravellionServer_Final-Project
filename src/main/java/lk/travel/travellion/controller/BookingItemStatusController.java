package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.bookingdto.BookingitemstatusDTO;
import lk.travel.travellion.service.booking.BookingItemStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/bookingitemstatuses")
@RequiredArgsConstructor
public class BookingItemStatusController {

    private final BookingItemStatusService bookingItemStatusService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<BookingitemstatusDTO>>> getBookingItemStatuses () {
        return ApiResponse.successResponse(bookingItemStatusService.getAllBookingItemStatus());
    }
}
