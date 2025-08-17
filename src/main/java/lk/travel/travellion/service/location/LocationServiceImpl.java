package lk.travel.travellion.service.location;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.travel.travellion.dto.locationdto.LocationListDTO;
import lk.travel.travellion.dto.locationdto.LocationRequestDTO;
import lk.travel.travellion.dto.locationdto.LocationResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Location;
import lk.travel.travellion.exceptions.ForeignKeyConstraintViolationException;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.LocationRepository;
import lk.travel.travellion.uitl.numberService.NumberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final ObjectMapper objectMapper;
    private final NumberService numberService;
    private final SpecificationBuilder specificationBuilder;

    /**
     * Retrieves all locations from the database and converts them to a list of {@code LocationResponseDTO}.
     * If filters are provided, the result will be filtered accordingly.
     *
     * @param filters a hashmap containing filter criteria where the key is the filter name and the value is the filter value.
     *                If null or empty, no filtering is applied.
     * @return a list of {@code LocationResponseDTO} representing all locations in the database,
     *         optionally filtered based on the provided filters.
     */
    @Transactional(readOnly = true)
    @Override
    public List<LocationResponseDTO> getAllLocations(HashMap<String, String> filters) {
        List<Location> locations = locationRepository.findAll();
        List<LocationResponseDTO> locationResponseDTOs = objectMapper.toLocationResponseDTOs(locations);
        if (filters == null || filters.isEmpty()) {
            return locationResponseDTOs;
        }

        try {
            Specification<Location> locationSpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toLocationResponseDTOs(locationRepository.findAll(locationSpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Retrieves a list of all locations and maps them to a list of LocationListDTO objects.
     *
     * @return a list of LocationListDTO objects representing all locations in the system.
     */
    @Transactional(readOnly = true)
    @Override
    public List<LocationListDTO> getAllLocationList() {
        return objectMapper.toLocationListDTOs(locationRepository.findAll());
    }

    /**
     * Retrieves a unique location code based on the provided city code.
     * The location code is generated using the city code and additional logic for uniqueness.
     *
     * @param citiCode the code representing the city for which the location code is to be retrieved
     * @return a unique location code specific to the provided city code
     */
    @Transactional(readOnly = true)
    @Override
    public String getLocationCode(String citiCode) {
        return numberService.generateLocationCode(citiCode);
    }

    /**
     * Saves a new location based on the provided location request data transfer object (DTO).
     * Converts the DTO into a Location entity, checks for uniqueness of the location code,
     * and saves it to the repository if it doesn't already exist.
     *
     * @param locationRequestDTO the data transfer object containing the details of the location to be saved
     * @return the saved Location entity
     * @throws ResourceAlreadyExistException if a location with the given code already exists
     */
    @Override
    public Location saveLocation(LocationRequestDTO locationRequestDTO) {

        Location locationEntity = objectMapper.toLocationEntity(locationRequestDTO);

        if (locationRepository.existsByCode(locationEntity.getCode())) {
            throw new ResourceAlreadyExistException("Location with code " + locationEntity.getCode() + " already exists");
        }

        return locationRepository.save(locationEntity);
    }

    /**
     * Updates an existing Location entity with the details provided in the LocationRequestDTO.
     * If the Location with the specified ID does not exist, it throws a ResourceNotFoundException.
     * If the new code conflicts with another existing Location's code, it throws a ResourceAlreadyExistException.
     *
     * @param locationRequestDTO the data transfer object containing the updated information for the Location
     * @return the updated Location entity after persisting the changes to the database
     * @throws ResourceNotFoundException if the Location with the given ID does not exist
     * @throws ResourceAlreadyExistException if a Location with the given code and a different ID already exists
     */
    @Override
    public Location updateLocation(LocationRequestDTO locationRequestDTO) {

        Location existingLocation = locationRepository.findById(locationRequestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Location with id " + locationRequestDTO.getId() + " not found"));

        if (!existingLocation.getCode().equals(locationRequestDTO.getCode()) &&
                locationRepository.existsByCodeAndIdNot(locationRequestDTO.getCode(), existingLocation.getId())) {
            throw new ResourceAlreadyExistException("Location with code " + locationRequestDTO.getCode() + " already exists");
        }
        return locationRepository.save(objectMapper.toLocationEntity(locationRequestDTO));
    }

    /**
     * Deletes a location identified by its unique ID.
     *
     * @param id the unique identifier of the location to be deleted
     * @throws ResourceNotFoundException if no location with the given ID exists
     */
    @Override
    public void deleteLocation(Integer id) {
        try {
            locationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Location with id " + id + " not found"));
            locationRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("Cannot delete location: Caused by having multiple associations");
        }
    }
}
