package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.RatetypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Ratetype;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.RatetypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RateTypeServiceImpl implements RateTypeService {

    private final RatetypeRepository ratetypeRepository;
    private final ObjectMapper objectMapper;

    /**
     * Retrieves all rate types from the repository and converts them into a list of DTOs.
     *
     * @return a list of {@code RatetypeDTO} objects representing all rate types.
     */
    @Transactional(readOnly = true)
    @Override
    public List<RatetypeDTO> getAllRateTypes() {
        return objectMapper.toRateTypeDTOs(ratetypeRepository.findAll());
    }

    /**
     * Saves a new RateType entity based on the provided RateTypeDTO.
     * Converts the DTO into an entity, checks if a RateType with the same name already exists,
     * and persists the entity if it does not exist.
     *
     * @param ratetypeDTO the data transfer object containing details of the RateType to save
     * @return the saved RateType entity
     * @throws ResourceAlreadyExistException if a RateType with the same name already exists
     */
    @Override
    public Ratetype saveRateType(RatetypeDTO ratetypeDTO) {

        Ratetype ratetype = objectMapper.toRateTypeEntity(ratetypeDTO);

        if (ratetypeRepository.existsByName(ratetype.getName())) {
            throw new ResourceAlreadyExistException("RateType with name " + ratetype.getName() + " already exists");
        }
        return ratetypeRepository.save(ratetype);
    }

    /**
     * Updates an existing RateType entity based on the provided RateTypeDTO.
     * This method checks for the existence of the RateType by its ID and validates name conflicts before updating.
     *
     * @param ratetypeDTO the data transfer object representing the RateType to be updated, containing the ID and new values
     * @return the updated RateType entity after saving to the database
     * @throws ResourceNotFoundException if the RateType with the given ID does not exist
     * @throws ResourceAlreadyExistException if a RateType with the same name already exists for a different ID
     */
    @Override
    public Ratetype updateRateType(RatetypeDTO ratetypeDTO) {
        Ratetype ratetype = objectMapper.toRateTypeEntity(ratetypeDTO);

        Ratetype existingRateType = ratetypeRepository.findById(ratetypeDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("RateType with " + ratetype.getName() + "not found"));

        if (!existingRateType.getName().equals(ratetype.getName()) &&
            ratetypeRepository.existsByNameAndIdNot(ratetypeDTO.getName(), existingRateType.getId())) {
            throw new ResourceAlreadyExistException("RateType with name " + ratetypeDTO.getName() + " already exists");
        }

        return ratetypeRepository.save(ratetype);
    }

    /**
     * Deletes the RateType entity with the specified ID. If the entity is not found,
     * a ResourceNotFoundException is thrown.
     *
     * @param id the unique identifier of the RateType to be deleted
     * @throws ResourceNotFoundException if no RateType is found with the given ID
     */
    @Override
    public void deleteRateType(Integer id) {
        ratetypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("RateType with id " + id + " not found"));
        ratetypeRepository.deleteById(id);
    }
}
