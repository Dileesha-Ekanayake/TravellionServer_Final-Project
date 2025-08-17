package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.RoomfacilityDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Roomfacility;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.RoomfacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomFacilitiesServiceImpl implements RoomFacilitiesService {

    private final RoomfacilityRepository roomfacilityRepository;
    private final ObjectMapper objectMapper;

    /**
     * Retrieves a list of all room facilities from the database and converts them into RoomfacilityDTO objects.
     *
     * @return a list of {@code RoomfacilityDTO} representing all room facilities.
     */
    @Transactional(readOnly = true)
    @Override
    public List<RoomfacilityDTO> getAllRoomFacilities() {
        return objectMapper.toRoomFacilityDTOs(roomfacilityRepository.findAll());
    }

    /**
     * Saves a new room facility to the database. If a room facility with the same name
     * already exists, a ResourceAlreadyExistException is thrown.
     *
     * @param roomfacilityDTO the data transfer object containing the details of the room facility to be saved
     * @return the saved Roomfacility entity
     * @throws ResourceAlreadyExistException if a room facility with the same name already exists
     */
    @Override
    public Roomfacility saveRoomFacility(RoomfacilityDTO roomfacilityDTO) {

        Roomfacility roomfacility = objectMapper.toRoomFacilityEntity(roomfacilityDTO);

        if (roomfacilityRepository.existsByName(roomfacility.getName())) {
            throw new ResourceAlreadyExistException("Room Facility with name " + roomfacility.getName() + " already exists");
        }
        return roomfacilityRepository.save(roomfacility);
    }

    /**
     * Updates an existing Roomfacility entity using the data provided in the RoomfacilityDTO.
     *
     * @param roomfacilityDTO the DTO containing the updated data for the Roomfacility entity
     * @return the updated Roomfacility entity after saving the changes to the database
     * @throws ResourceNotFoundException if no Roomfacility entity is found with the ID provided in the RoomfacilityDTO
     * @throws ResourceAlreadyExistException if a Roomfacility with the same name already exists and is not the current Roomfacility being updated
     */
    @Override
    public Roomfacility updateRoomFacility(RoomfacilityDTO roomfacilityDTO) {
        Roomfacility exisitingRoomFacility = roomfacilityRepository.findById(roomfacilityDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Room facility with id " + roomfacilityDTO.getId() + " not found"));

        if (!exisitingRoomFacility.getName().equals(roomfacilityDTO.getName()) &&
            roomfacilityRepository.existsByNameAndIdNot(roomfacilityDTO.getName(), exisitingRoomFacility.getId())) {
            throw new ResourceAlreadyExistException("Room facility with name " + roomfacilityDTO.getName() + " already exists");
        }

        return roomfacilityRepository.save(objectMapper.toRoomFacilityEntity(roomfacilityDTO));
    }

    /**
     * Deletes a room facility identified by the given ID. If the specified ID does not exist,
     * a ResourceNotFoundException is thrown.
     *
     * @param id the unique identifier of the room facility to be deleted
     * @throws ResourceNotFoundException if a room facility with the specified ID is not found
     */
    @Override
    public void deleteRoomFacility(Integer id) {
        roomfacilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room Facility with id " + id + " not found"));
        roomfacilityRepository.deleteById(id);
    }
}
