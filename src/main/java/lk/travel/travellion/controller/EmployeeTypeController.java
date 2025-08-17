package lk.travel.travellion.controller;

import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.employeedto.EmployeetypeDTO;
import lk.travel.travellion.service.employee.EmployeeTypeService;
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
@RequestMapping("/employeetypes")
@RequiredArgsConstructor
public class EmployeeTypeController {

    private final EmployeeTypeService employeeTypeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<EmployeetypeDTO>>> geetEmployeeTypes() {
        return ApiResponse.successResponse(employeeTypeService.getAllEmployeeTypes());
    }
}
