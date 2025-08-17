package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.employeedto.EmployeestatusDTO;
import lk.travel.travellion.service.employee.EmployeeStatusService;
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
@RequestMapping("/employeestatuses")
@RequiredArgsConstructor
public class EmployeeStatusController {

    private final EmployeeStatusService employeeStatusService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<EmployeestatusDTO>>> getEmployeeStatuses() {
        return ApiResponse.successResponse(employeeStatusService.getAllEmployeeStatus());
    }
}
