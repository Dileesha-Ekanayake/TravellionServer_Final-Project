package lk.travel.travellion.service.employee;

import lk.travel.travellion.dto.employeedto.EmployeetypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.EmployeeTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeTypeService {

    private final EmployeeTypeRepository employeeTypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<EmployeetypeDTO> getAllEmployeeTypes() {
        return objectMapper.toEmployeeTypeDTOs(employeeTypeRepository.findAll());
    }
}
