package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.ResidenttypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Residenttype;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.ResidenttypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResidentTypeServiceImpl implements ResidentTypeService {

    private final ResidenttypeRepository residenttypeRepository;
    private final ObjectMapper objectMapper;

    /**
     * Retrieves all resident types from the repository and maps them to DTOs.
     *
     * @return a list of ResidenttypeDTO objects representing all resident types.
     */
    @Transactional(readOnly = true)
    @Override
    public List<ResidenttypeDTO> getAllResidentTypes() {
        return objectMapper.toResidentTypeDTOs(residenttypeRepository.findAll());
    }

    /**
     * Saves a new ResidentType entity based on the provided data transfer object (DTO).
     * Converts the DTO to an entity, checks for duplicate names, and saves the entity
     * to the repository if no conflict exists.
     *
     * @param residenttypeDTO the data transfer object containing the details of the ResidentType to save
     * @return the saved ResidentType entity
     * @throws ResourceAlreadyExistException if a ResidentType with the same name already exists
     */
    @Override
    public Residenttype saveResidentType(ResidenttypeDTO residenttypeDTO) {

        Residenttype residenttype = objectMapper.toResidentTypeEntity(residenttypeDTO);

        if (residenttypeRepository.existsByName(residenttype.getName())) {
            throw new ResourceAlreadyExistException("ResidentType with name " + residenttype.getName() + " already exists");
        }
        return residenttypeRepository.save(residenttype);
    }

    /**
     * Updates an existing ResidentType entity with the data provided in the given ResidenttypeDTO.
     *
     * @param residenttypeDTO the data transfer object containing the updated details for the ResidentType.
     *                        Must include the ID of the ResidentType to be updated and its new name.
     * @return the updated ResidentType entity after saving the changes to the repository.
     * @throws ResourceNotFoundException if the ResidentType with the given ID cannot be found
     *                                   or if a ResidentType with the specified name already exists.
     */
    @Override
    public Residenttype updateResidentType(ResidenttypeDTO residenttypeDTO) {
        Residenttype existingResidentType = residenttypeRepository.findById(residenttypeDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("ResidentType with id " + residenttypeDTO.getId() + " not found"));

        if (!existingResidentType.getName().equals(residenttypeDTO.getName()) &&
            residenttypeRepository.existsByNameAndIdNot(residenttypeDTO.getName(), existingResidentType.getId())) {
            throw new ResourceNotFoundException("ResidentType with name " + residenttypeDTO.getName() + " already exists");
        }

        return residenttypeRepository.save(objectMapper.toResidentTypeEntity(residenttypeDTO));
    }

    /**
     * Deletes a ResidentType entity with the given ID.
     * If the entity with the specified ID does not exist, a ResourceNotFoundException is thrown.
     *
     * @param id the ID of the ResidentType to be deleted
     * @throws ResourceNotFoundException if no ResidentType is found with the specified ID
     */
    @Override
    public void deleteResidentType(Integer id) {
        residenttypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ResidentType with id " + id + " not found"));
        residenttypeRepository.deleteById(id);
    }
}
