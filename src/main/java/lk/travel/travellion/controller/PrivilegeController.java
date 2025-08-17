package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.privilegedto.PrivilegeDTO;
import lk.travel.travellion.entity.Privilege;
import lk.travel.travellion.service.auth.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/privileges")
@RequiredArgsConstructor
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<PrivilegeDTO>>> getAllEmployees() {
        List<PrivilegeDTO> privileges = privilegeService.getAllPrivileges();
        return ApiResponse.successResponse(privileges);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody List<PrivilegeDTO> privilegeDTOList) {
        privilegeService.savePrivilege(privilegeDTOList);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Privileges are successfully added"
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody PrivilegeDTO privilege) {
        Privilege updatedPrivilege = privilegeService.updatePrivilege(privilege);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Privilege : " + updatedPrivilege.getId()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Integer id) {
        privilegeService.deletePrivilege(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Privilege ID : " + id
        );
    }
}
