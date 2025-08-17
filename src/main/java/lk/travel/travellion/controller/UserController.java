package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.userdto.UserActiveDeactiveDTO;
import lk.travel.travellion.dto.userdto.UserListDTO;
import lk.travel.travellion.dto.userdto.UserRequestDTO;
import lk.travel.travellion.dto.userdto.UserResponseDTO;
import lk.travel.travellion.entity.User;
import lk.travel.travellion.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers(@RequestParam(required = false) HashMap<String, String> filters) {
        List<UserResponseDTO> users = userService.getAllUsers(filters);
        return ApiResponse.successResponse(users);
    }

    @GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<UserListDTO>>> getAllUsersList() {
        return ApiResponse.successResponse(userService.getUserList());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveUser(@Valid @RequestBody UserRequestDTO user) {
        User savedUser = userService.saveUser(user);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "User : " + savedUser.getUsername()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateUser(@Valid @RequestBody UserRequestDTO user) {
        User updatedUser = userService.updateUser(user);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "User : " + updatedUser.getUsername()
        );
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Deleted User: " + username
        );
    }

    @PutMapping("/activateordeactivateuser")
    public ResponseEntity<ApiResponse<String>> activateOrDeactivateUser(@Valid @RequestBody UserActiveDeactiveDTO userActiveDeactiveDTO) {
        userService.activateOrDeactivateUser(userActiveDeactiveDTO);
        return ApiResponse.successResponse(
                "Successfully Modified",
                HttpStatus.OK,
                "User : " + userActiveDeactiveDTO.getUsername()
        );
    }
}
