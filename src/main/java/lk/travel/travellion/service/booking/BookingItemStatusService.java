package lk.travel.travellion.service.booking;

import lk.travel.travellion.dto.bookingdto.BookingitemstatusDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.BookingitemstatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingItemStatusService {

    private final BookingitemstatusRepository bookingitemstatusRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<BookingitemstatusDTO> getAllBookingItemStatus() {
        return objectMapper.toBookingItemStatusDTOs(bookingitemstatusRepository.findAll());
    }
}
