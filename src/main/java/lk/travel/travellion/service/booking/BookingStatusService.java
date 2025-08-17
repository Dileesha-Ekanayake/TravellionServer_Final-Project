package lk.travel.travellion.service.booking;

import lk.travel.travellion.dto.bookingdto.BookingstatusDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.BookingstatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingStatusService {

    private final BookingstatusRepository bookingstatusRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<BookingstatusDTO> getAllBookingStatus() {
        return objectMapper.toBookingStatusDTOs(bookingstatusRepository.findAll());
    }
}
