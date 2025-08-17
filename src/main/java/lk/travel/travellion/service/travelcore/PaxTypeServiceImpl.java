package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.PaxtypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Paxtype;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.PaxtypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaxTypeServiceImpl implements PaxTypeService {

    private final PaxtypeRepository paxtypeRepository;
    private final ObjectMapper objectMapper;

    /**
     * Retrieves a list of all PaxTypes from the repository and converts them to DTOs.
     *
     * @return a list of {@link PaxtypeDTO} objects representing all PaxTypes in the database
     */
    @Transactional(readOnly = true)
    @Override
    public List<PaxtypeDTO> getAllPaxTypes() {
        return objectMapper.toPaxTypeDTOs(paxtypeRepository.findAll());
    }

    /**
     * Saves a new PaxType entity created from the provided {@code PaxtypeDTO}.
     * Ensures the PaxType name does not already exist in the repository before saving.
     *
     * @param paxtypeDTO the data transfer object containing the information to create a new PaxType
     * @return the saved Paxtype entity
     * @throws ResourceAlreadyExistException if a PaxType with the same name already exists in the repository
     */
    @Override
    public Paxtype savePaxType(PaxtypeDTO paxtypeDTO) {

        Paxtype paxtype = objectMapper.toPaxTypeEntity(paxtypeDTO);

        if (paxtypeRepository.existsByName(paxtype.getName())) {
            throw new ResourceAlreadyExistException("PaxType with name " + paxtype.getName() + " already exists");
        }
        return paxtypeRepository.save(paxtype);
    }

    /**
     * Updates an existing PaxType entity with new details provided in the PaxtypeDTO.
     * If the PaxType with the given ID does not exist, a ResourceNotFoundException is thrown.
     * If another PaxType with the same name already exists (excluding the current PaxType ID),
     * a ResourceNotFoundException is thrown to indicate the duplication.
     *
     * @param paxtypeDTO the data transfer object containing the updated details of the PaxType
     * @return the updated PaxType entity after saving the changes
     * @throws ResourceNotFoundException if the PaxType with the given ID is not found
     * @throws ResourceNotFoundException if another PaxType with the same name already exists
     */
    @Override
    public Paxtype updatePaxType(PaxtypeDTO paxtypeDTO) {
        Paxtype existingPaxType = paxtypeRepository.findById(paxtypeDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("PaxType with id " + paxtypeDTO.getId() + " not found"));

        if (!existingPaxType.getName().equals(paxtypeDTO.getName()) &&
            paxtypeRepository.existsByNameAndIdNot(paxtypeDTO.getName(), existingPaxType.getId())) {
            throw new ResourceNotFoundException("PaxType with name " + paxtypeDTO.getName() + " already exists");
        }

        return paxtypeRepository.save(objectMapper.toPaxTypeEntity(paxtypeDTO));
    }

    /**
     * Deletes a PaxType entity specified by its unique identifier.
     * If the PaxType with the given ID does not exist, a ResourceNotFoundException is thrown.
     *
     * @param id the unique identifier of the PaxType to be deleted
     * @throws ResourceNotFoundException if no PaxType with the given ID is found
     */
    @Override
    public void deletePaxType(Integer id) {
        paxtypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PaxType with id " + id + " not found"));
        paxtypeRepository.deleteById(id);
    }
}
