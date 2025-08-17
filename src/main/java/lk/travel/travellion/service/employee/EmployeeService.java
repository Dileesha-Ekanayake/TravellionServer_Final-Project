package lk.travel.travellion.service.employee;

import lk.travel.travellion.dto.employeedto.EmployeeListDTO;
import lk.travel.travellion.dto.employeedto.EmployeeRequestDTO;
import lk.travel.travellion.dto.employeedto.EmployeeResponseDTO;
import lk.travel.travellion.dto.employeedto.EmployeeforUserDetailsDTO;
import lk.travel.travellion.entity.Employee;

import java.util.HashMap;
import java.util.List;

public interface EmployeeService {
    
    List<EmployeeResponseDTO> getAllEmployees(HashMap<String, String> filters);

    List<EmployeeListDTO> getEmployeeList();

    EmployeeforUserDetailsDTO getAllEmployeeForUserDetails(Integer employeeId);

    EmployeeforUserDetailsDTO getEmployeeByLogInUser(String username);

    String getEmployeeNumber();

    Employee saveEmployee(EmployeeRequestDTO employeeRequestDTO);

    Employee updateEmployee(EmployeeRequestDTO employeeRequestDTO);

    void deleteEmployee(Integer id);
}
