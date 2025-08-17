package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.employeedto.EmployeeListDTO;
import lk.travel.travellion.dto.employeedto.EmployeeRequestDTO;
import lk.travel.travellion.dto.employeedto.EmployeeResponseDTO;
import lk.travel.travellion.dto.employeedto.EmployeeforUserDetailsDTO;
import lk.travel.travellion.entity.Employee;
import lk.travel.travellion.service.employee.EmployeeService;
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
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

//    @PreAuthorize("hasAuthority('employee-select')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<EmployeeResponseDTO>>> getAllEmployees(@RequestParam(required = false) HashMap<String, String> filters) {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees(filters);
        return ApiResponse.successResponse(employees);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<EmployeeListDTO>>> getEmployeeList() {
        List<EmployeeListDTO> employeeListDTOs = employeeService.getEmployeeList();
        return ApiResponse.successResponse(employeeListDTOs);
    }

    @GetMapping(path = "/employee-number", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, String>>> getEmployeeNumber(){
        Map<String, String> response = new HashMap<>();
        response.put("employeeNumber", employeeService.getEmployeeNumber());
        return ApiResponse.successResponse(response);
    }

    @GetMapping(path = "/employee-for-user-details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ApiResponse<EmployeeforUserDetailsDTO>> getEmployeeForUserDetails(@PathVariable("id") Integer employeeId) {
        EmployeeforUserDetailsDTO employeeforUserDetailsDTO = employeeService.getAllEmployeeForUserDetails(employeeId);
        return ApiResponse.successResponse(employeeforUserDetailsDTO);
    }

    @GetMapping(path = "/employee-by-username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<ApiResponse<EmployeeforUserDetailsDTO>> getEmployeeByUsername(@PathVariable("username") String username) {
        EmployeeforUserDetailsDTO employeeforUserDetailsDTO = employeeService.getEmployeeByLogInUser(username);
        return ApiResponse.successResponse(employeeforUserDetailsDTO);
    }

//    @PreAuthorize("hasAuthority('employee-create')")
    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveEmployee(@Valid @RequestBody EmployeeRequestDTO employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Employee : " + savedEmployee.getCallingname()
        );
    }

//    @PreAuthorize("hasAuthority('employee-update')")
    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateEmployee(@Valid @RequestBody EmployeeRequestDTO employee) {
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Employee : " + updatedEmployee.getCallingname()
        );
    }

//    @PreAuthorize("hasAuthority('employee-delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Employee ID : " + id
        );
    }
}
