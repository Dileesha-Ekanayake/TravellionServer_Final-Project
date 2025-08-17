package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.RoomtypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Roomtype;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.RoomtypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomtypeRepository roomtypeRepository;
    private final ObjectMapper objectMapper;

    /**
     * Retrieves a list of all room types.
     *
     * @return a list of {@link RoomtypeDTO} objects representing all available room types
     */
    @Transactional(readOnly = true)
    @Override
    public List<RoomtypeDTO> getAllRoomTypes() {
        return objectMapper.toRoomTypeDTOs(roomtypeRepository.findAll());
    }

    /**
     * Saves a new Roomtype entity based on the provided RoomtypeDTO object.
     * Converts the RoomtypeDTO to a Roomtype entity and persists it in the
     * database if a Roomtype with the same name does not already exist.
     *
     * @param roomtypeDTO the data transfer object containing the Roomtype details to be saved
     * @return the saved Roomtype entity
     * @throws ResourceAlreadyExistException if a Roomtype with the same name already exists
     */
    @Override
    public Roomtype saveRoomType(RoomtypeDTO roomtypeDTO) {

        Roomtype roomtype = objectMapper.toRoomTypeEntity(roomtypeDTO);

        if (roomtypeRepository.existsByName(roomtype.getName())) {
            throw new ResourceAlreadyExistException("Room Type with name " + roomtype.getName() + " already exists");
        }
        return roomtypeRepository.save(roomtype);
    }

    /**
     * Updates an existing Roomtype entity based on the provided RoomtypeDTO.
     * Ensures that the Roomtype exists and does not conflict with an existing Roomtype's name.
     *
     * @param roomtypeDTO the data transfer object containing the updated details of the Roomtype
     * @return the updated Roomtype entity
     * @throws ResourceNotFoundException if the Roomtype with the specified ID does not exist
     * @throws ResourceAlreadyExistException if a Roomtype with the updated name already exists
     *         under a different ID
     */
    @Override
    public Roomtype updateRoomType(RoomtypeDTO roomtypeDTO) {

        Roomtype existingRoomType = roomtypeRepository.findById(roomtypeDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Room type with id " + roomtypeDTO.getId() + " not found"));

        if (!existingRoomType.getName().equals(roomtypeDTO.getName()) &&
            roomtypeRepository.existsByNameAndIdNot(roomtypeDTO.getName(), existingRoomType.getId())) {
            throw new ResourceAlreadyExistException("Room type with name " + roomtypeDTO.getName() + " already exists");
        }

        return roomtypeRepository.save(objectMapper.toRoomTypeEntity(roomtypeDTO));
    }

    /**
     * Deletes a room type specified by its unique identifier.
     * This method removes the corresponding room type
     * entry from the data repository. Throws a {@link ResourceNotFoundException}
     * if the room type with the specified ID does not exist.
     *
     * @param id the unique identifier of the room type to be deleted
     */
    @Override
    public void deleteRoomType(Integer id) {
        roomtypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room Type with id " + id + " not found"));
        roomtypeRepository.deleteById(id);
    }
}
