package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.bookingdto.BookingstatusDTO;
import lk.travel.travellion.service.booking.BookingStatusService;
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
@RequestMapping("/bookingstatuses")
@RequiredArgsConstructor
public class BookingStatusController {

    private final BookingStatusService bookingStatusService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<BookingstatusDTO>>> getBookingStatuses () {
        return ApiResponse.successResponse(bookingStatusService.getAllBookingStatus());
    }
}
