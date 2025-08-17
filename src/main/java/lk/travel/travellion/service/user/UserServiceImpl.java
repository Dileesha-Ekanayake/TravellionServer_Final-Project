package lk.travel.travellion.service.user;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.travel.travellion.dto.userdto.UserActiveDeactiveDTO;
import lk.travel.travellion.dto.userdto.UserListDTO;
import lk.travel.travellion.dto.userdto.UserRequestDTO;
import lk.travel.travellion.dto.userdto.UserResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.User;
import lk.travel.travellion.entity.Userstatus;
import lk.travel.travellion.exceptions.ForeignKeyConstraintViolationException;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.UserRepository;
import lk.travel.travellion.repository.UserStatusRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${user.status.active}")
    private int active;
    @Value("${user.status.locked}")
    private int locked;
    @Value("${user.default.locked.status:false}")
    private boolean defaultStatus;
    @Value("${user.default.user.status}")
    private int defaultUserStatus;

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    private final SpecificationBuilder specificationBuilder;

    /**
     * Retrieves a list of all users and applies optional filters to refine the result.
     *
     * @param filters a map of filter keys and corresponding filter values to apply to the user list.
     *                If null or empty, no filters will be applied.
     * @return a list of UserResponseDTO objects representing the users that match the criteria.
     * @throws ResourceNotFoundException if any invalid or unsupported filter key is provided.
     */
    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDTO> getAllUsers(HashMap<String, String> filters) {
        List<User> users = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOS = objectMapper.toUserResponseDTOs(users);

        if (filters == null || filters.isEmpty()) {
            return userResponseDTOS;
        }

        try {
            Specification<User> userSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toUserResponseDTOs(userRepository.findAll(userSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of users who have active accounts and match the default user status.
     *
     * @return a list of UserListDTO objects representing users with unlocked accounts and the default user status.
     */
    @Transactional(readOnly = true)
    @Override
    public List<UserListDTO> getUserList() {
        return objectMapper.toUserListDTOs(userRepository.findByAccountLockedFalseAndUserstatus_Id(defaultUserStatus));
    }

    /**
     * Saves a new user to the system. If the username already exists, a {@code ResourceAlreadyExistException}
     * is thrown. Ensures the user is assigned a default active status during the save operation.
     *
     * @param userRequestDTO the data transfer object containing user information to be saved
     * @return the saved {@code User} entity
     * @throws ResourceAlreadyExistException if a user with the specified username already exists
     * @throws TransactionRollbackException if there's an error during the database transaction
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public User saveUser(UserRequestDTO userRequestDTO) {

        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new ResourceAlreadyExistException("User with name : " + userRequestDTO.getUsername() + " already exists");
        }

        try {
            User userEntity = objectMapper.toUserEntity(userRequestDTO);
            Optional.ofNullable(userEntity.getUserroles())
                    .ifPresent(userroles -> userroles.forEach(userrole ->
                        userrole.setUser(userEntity)
                    ));

            // Always ensure when initially creating users, all have active status
            if (userEntity.getUserstatus().getId() != defaultUserStatus){
                Userstatus status = userStatusRepository.findById(defaultUserStatus)
                        .orElseThrow(() -> new ResourceNotFoundException("User status not found"));
                userEntity.setUserstatus(status);
            }

            userEntity.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
            userEntity.setAccountLocked(defaultStatus);
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving User Data", e);
        }
    }

    /**
     * Updates an existing user in the system using the provided user request data transfer object.
     * This method also validates the username and handles role updates, password encoding, and other fields.
     * Performs transactional updates with a rollback in case of failures.
     *
     * @param userRequestDTO the data transfer object containing user details to be updated
     * @return the updated User entity after successful update and persistence
     * @throws ResourceNotFoundException if the user to be updated does not exist
     * @throws ResourceAlreadyExistException if the username already exists for another user
     * @throws TransactionRollbackException if an error occurs during the update process that requires a rollback
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public User updateUser(UserRequestDTO userRequestDTO) {

        User existingUser = userRepository.findByUsername(userRequestDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User with username : " + userRequestDTO.getUsername() + " not found"));

        if (!existingUser.getUsername().equals(userRequestDTO.getUsername()) &&
                userRepository.existsByUsernameAndIdNot(userRequestDTO.getUsername(), existingUser.getId())) {
            throw new ResourceAlreadyExistException("User with name: " + userRequestDTO.getUsername() + " already exists");
        }

        try {
            User userEntity = objectMapper.toUserEntity(userRequestDTO);
            // Update existing user's userroles
            existingUser.getUserroles().clear();
            Optional.ofNullable(userEntity.getUserroles())
                    .ifPresent(userroles -> userroles.forEach(userrole -> {
                        userrole.setUser(existingUser);
                        existingUser.getUserroles().add(userrole);
                    }));

            if ((userEntity.getPassword() != null && !userEntity.getPassword().isEmpty()) && !userEntity.getPassword().equals("TravellionTempPass@25")) {
                // Check if the password has changed
                if (!passwordEncoder.matches(userEntity.getPassword(), existingUser.getPassword())) {
                    existingUser.setPassword(passwordEncoder.encode(userEntity.getPassword()));
                }
            }

            // copy other data to existingUser from updated user
            BeanUtils.copyProperties(userEntity, existingUser, "id", "userroles", "password", "createdon", "updatedon");
            userEntity.setAccountLocked(defaultStatus);
            // save existingUser with all updated values
            return userRepository.save(existingUser);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Updating User Data", e);
        }
    }

    /**
     * Deletes a user based on their username.
     *
     * @param username the username of the user to be deleted
     * @throws ResourceNotFoundException if no user with the specified username is found
     */
    @Override
    public void deleteUser(String username) {

        try {
            User deletedUser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User with username : " + username + " not found"));

            userRepository.deleteById(deletedUser.getId());
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("Cannot delete user: Caused by having a user account");
        }
    }

    /**
     * Activates or deactivates a user account based on the status provided in the UserActiveDeactiveDTO.
     * Updates the user's account lock status and associated user status in the database.
     *
     * @param userActiveDeactiveDTO the data transfer object containing the username of the user to be activated or deactivated
     *                              and the account lock status indicating whether the account should be active or locked
     */
    @Override
    public void activateOrDeactivateUser(UserActiveDeactiveDTO userActiveDeactiveDTO) {

        User foundedUser = userRepository.findByUsername(userActiveDeactiveDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + userActiveDeactiveDTO.getUsername() + " not found"));

        boolean isLocked = Boolean.TRUE.equals(userActiveDeactiveDTO.getAccountLocked());
        int statusId = isLocked ? locked : active;

        Userstatus status = userStatusRepository.findById(statusId)
                .orElseThrow(() -> new ResourceNotFoundException("User status not found"));

        foundedUser.setAccountLocked(isLocked);
        foundedUser.setUserstatus(status);
        userRepository.save(foundedUser);
    }

}
