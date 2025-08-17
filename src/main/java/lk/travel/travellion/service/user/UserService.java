package lk.travel.travellion.service.user;

import lk.travel.travellion.dto.userdto.UserActiveDeactiveDTO;
import lk.travel.travellion.dto.userdto.UserListDTO;
import lk.travel.travellion.dto.userdto.UserRequestDTO;
import lk.travel.travellion.dto.userdto.UserResponseDTO;
import lk.travel.travellion.entity.User;

import java.util.HashMap;
import java.util.List;

public interface UserService {
   
    List<UserResponseDTO> getAllUsers(HashMap<String, String> filters);

    List<UserListDTO> getUserList();

    User saveUser(UserRequestDTO userRequestDTO);

    User updateUser(UserRequestDTO userRequestDTO);

    void deleteUser(String username);

    void activateOrDeactivateUser(UserActiveDeactiveDTO userActiveDeactiveDTO);
}
