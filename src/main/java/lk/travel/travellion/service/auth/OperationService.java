package lk.travel.travellion.service.auth;

import lk.travel.travellion.dto.operationdto.OperationDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Operation;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;
    private final ObjectMapper objectMapper;

    /**
     * Retrieves all available operations from the system.
     *
     * @return a list of {@code OperationDTO} objects representing all operations.
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> getAllOperations() {
        return objectMapper.toOperationDTOs(operationRepository.findAll());
    }

    /**
     * Saves an operation by converting the given OperationDTO into an operation entity
     * and storing it in the repository.
     *
     * @param operation the DTO object containing the details of the operation to be saved
     * @return the saved operation entity
     */
    public Operation saveOperation(OperationDTO operation) {

        return operationRepository.save(objectMapper.toOperationEntity(operation));
    }

    /**
     * Retrieves a list of operations associated with the specified module ID.
     *
     * @param moduleId the unique identifier of the module for which the operations are to be retrieved
     *                 (must not be null).
     * @return a list of {@code OperationDTO} objects representing the operations associated with the given module ID.
     */
    @Transactional(readOnly = true)
    public List<OperationDTO> getOperationByModuleId(Integer  moduleId) {
       return objectMapper.toOperationDTOs(operationRepository.findByModule_Id(moduleId));
    }

    /**
     * Deletes an operation identified by the given ID. If the operation
     * with the specified ID does not exist, a ResourceNotFoundException is thrown.
     *
     * @param id the ID of the operation to be deleted
     * @throws ResourceNotFoundException if an operation with the specified ID is not found
     */
    public void deleteOperation(Integer id) {

        operationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Operation with id " + id + " not found"));

        operationRepository.deleteById(id);
    }

}
