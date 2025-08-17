package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.CancellationschemeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Cancellationscheme;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.CancellationschemeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CancellationSchemeServiceImpl implements CancellationSchemeService {

    private final CancellationschemeRepository cancellationschemeRepository;
    private final ObjectMapper objectMapper;

    /**
     * Retrieves all cancellation schemes as a list of CancellationschemeDTO objects.
     *
     * @return a list of CancellationschemeDTO instances representing all cancellation schemes.
     */
    @Transactional(readOnly = true)
    @Override
    public List<CancellationschemeDTO> getAllCancellationSchemes() {
        return objectMapper.toCancellationSchemeDTOs(cancellationschemeRepository.findAll());
    }

    /**
     * Saves a new cancellation scheme to the repository.
     *
     * @param cancellationschemeDTO the DTO containing the details of the cancellation scheme to be saved
     * @return the saved cancellation scheme entity
     * @throws ResourceAlreadyExistException if a cancellation scheme with the same name already exists
     */
    @Override
    public Cancellationscheme saveCancellationScheme(CancellationschemeDTO cancellationschemeDTO) {

        if (cancellationschemeRepository.existsByName(cancellationschemeDTO.getName())) {
            throw new ResourceAlreadyExistException("CancellationScheme with name " + cancellationschemeDTO.getName() + " already exists");
        }
        return cancellationschemeRepository.save(objectMapper.toCancellationSchemeEntity(cancellationschemeDTO));
    }

    /**
     * Updates an existing cancellation scheme based on the provided data transfer object (DTO).
     *
     * This method retrieves the existing cancellation scheme by its ID from the repository,
     * validates if the provided data does not violate any unique constraints, and updates
     * the entity in the database.
     *
     * @param cancellationschemeDTO the DTO containing the updated details of the cancellation scheme
     * @return the updated cancellation scheme entity
     * @throws ResourceNotFoundException if the cancellation scheme with the specified ID does not exist
     * @throws ResourceAlreadyExistException if a cancellation scheme with the provided name already exists
     *                                       for a different ID
     */
    @Override
    public Cancellationscheme updateCancellationScheme(CancellationschemeDTO cancellationschemeDTO) {

        Cancellationscheme existingCancellationScheme = cancellationschemeRepository.findById(cancellationschemeDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("CancellationScheme with " + cancellationschemeDTO.getName() + "not found"));

        if (!existingCancellationScheme.getName().equals(cancellationschemeDTO.getName()) &&
                cancellationschemeRepository.existsByNameAndIdNot(cancellationschemeDTO.getName(), existingCancellationScheme.getId())) {
            throw new ResourceAlreadyExistException("CancellationScheme with name " + cancellationschemeDTO.getName() + " already exists");
        }
        
        return cancellationschemeRepository.save(objectMapper.toCancellationSchemeEntity(cancellationschemeDTO));
    }

    /**
     * Deletes the cancellation scheme with the specified ID.
     * If no cancellation scheme with the given ID exists, a ResourceNotFoundException is thrown.
     *
     * @param id the ID of the cancellation scheme to be deleted
     * @throws ResourceNotFoundException if the cancellation scheme with the specified ID is not found
     */
    @Override
    public void deleteCancellationScheme(Integer id) {
        cancellationschemeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CancellationScheme with id " + id + " not found"));
        cancellationschemeRepository.deleteById(id);
    }
}
