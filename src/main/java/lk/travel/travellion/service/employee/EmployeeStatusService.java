package lk.travel.travellion.service.employee;

import lk.travel.travellion.dto.employeedto.EmployeestatusDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.EmployeeStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeStatusService {

    private final EmployeeStatusRepository employeeStatusRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<EmployeestatusDTO> getAllEmployeeStatus() {
        return objectMapper.toEmployeeStatusDTOs(employeeStatusRepository.findAll());
    }
}
