package lk.travel.travellion.service.location;

import lk.travel.travellion.dto.locationdto.AirportListDTO;
import lk.travel.travellion.dto.locationdto.AirportResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<AirportResponseDTO> getAllAirports() {
        return objectMapper.toAirportResponseDTOs(airportRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<AirportListDTO> getAirportList() {
        return objectMapper.toAirportListDTOs(airportRepository.findAll());
    }
}
