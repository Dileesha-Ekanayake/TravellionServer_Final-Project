package lk.travel.travellion.service.auth;

import lk.travel.travellion.dto.privilegedto.PrivilegeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Module;
import lk.travel.travellion.entity.Privilege;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final ObjectMapper objectMapper;

    /**
     * Retrieves all privileges from the system.
     *
     * @return a list of {@code PrivilegeDTO} objects representing all available privileges.
     */
    @Transactional(readOnly = true)
    @Override
    public List<PrivilegeDTO> getAllPrivileges() {
        return objectMapper.toPrivilegeDTOs(privilegeRepository.findAll());
    }

    /**
     * Saves a list of privileges by performing the following operations:
     * 1. Deletes old privileges associated with the given roles and module IDs
     *    if the `operation` is null and `authority` is empty.
     * 2. Deletes old privileges associated with the given module IDs
     *    if there are modified privileges with non-null `operation`
     *    and non-empty `authority`.
     * 3. Converts the provided privileges from DTOs to entities and saves them.
     *
     * The operation is performed within a transaction and rolls back in case
     * of an exception during the database operations.
     *
     * @param privileges the list of privilege DTOs to be saved or updated.
     *                   Must not be null. If the list is empty, the method
     *                   performs no operation.
     * @throws TransactionRollbackException if any database operation fails during processing.
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public void savePrivilege(List<PrivilegeDTO> privileges) {
        if (privileges.isEmpty()) return;

        try {
            // Delete old privileges based on role and module ID if operation is null and authority is empty
            List<PrivilegeDTO> toDelete = privileges.stream()
                    .filter(privilege -> privilege.getOperation() == null && privilege.getAuthority().isEmpty())
                    .toList();

            if (!toDelete.isEmpty()) {
                toDelete.forEach(privilege ->
                        privilegeRepository.deleteAllByRole_IdAndModule_Id(privilege.getRole().getId(), privilege.getModule().getId()));
                return; // Exit early, only deleting old privileges
            }

            // operation is not null and authority is not empty
            // mean have modified privileges so initially clear the old privileges and save as new
            // Delete old privileges by module id
            privileges.stream()
                    .map(PrivilegeDTO::getModule) // extract the only module
                    .map(Module::getId) // extract the module id
                    .distinct() // ignore duplicates, only delete once per Module ID
                    .forEach(privilegeRepository::deleteAllByModule_Id);

            // Convert DTOs to entities
            List<Privilege> privilegeEntities = privileges.stream()
                    .map(objectMapper::toPrivilegeEntity)
                    .toList();

            // Save new privileges
            privilegeRepository.saveAll(privilegeEntities);

        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving Privilege Data", e);
        }
    }


    /**
     * Updates an existing Privilege entity using the provided PrivilegeDTO.
     *
     * @param privilege the data transfer object containing the updated information for the Privilege entity
     * @return the updated Privilege entity after persisting the changes in the database
     */
    @Override
    public Privilege updatePrivilege(PrivilegeDTO privilege) {

        return privilegeRepository.save(objectMapper.toPrivilegeEntity(privilege));

    }

    /**
     * Deletes a privilege by its unique identifier. If the privilege with the specified ID does not exist,
     * a {@link ResourceNotFoundException} is thrown.
     *
     * @param id the unique identifier of the privilege to be deleted
     * @throws ResourceNotFoundException if no privilege is found with the given ID
     */
    @Override
    public void deletePrivilege(Integer id) {
        privilegeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Privilege with id " + id + " not found")
        );

        privilegeRepository.deleteById(id);
    }
}
