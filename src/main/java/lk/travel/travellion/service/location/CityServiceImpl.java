package lk.travel.travellion.service.location;

import lk.dileesha.jpafilter.SpecificationBuilder;
import lk.travel.travellion.dto.locationdto.CityListDTO;
import lk.travel.travellion.dto.locationdto.CityRequestDTO;
import lk.travel.travellion.dto.locationdto.CityResponseDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.City;
import lk.travel.travellion.exceptions.ForeignKeyConstraintViolationException;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.exceptions.TransactionRollbackException;
import lk.travel.travellion.repository.CityRepository;
import lk.travel.travellion.uitl.numberService.NumberService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final ObjectMapper objectMapper;
    private final NumberService numberService;
    private final SpecificationBuilder specificationBuilder;

    /**
     * Retrieves a list of all cities, with optional filtering based on the provided parameters.
     *
     * @param filters a map of filter parameters where key is the filter field (e.g., "airports", "ports")
     *                and value is the corresponding filter value to match against.
     * @return a list of {@link CityResponseDTO} representing cities that match the specified filters,
     *         or all cities if no filters are provided.
     */
    @Transactional(readOnly = true)
    @Override
    public List<CityResponseDTO> getAllCities(HashMap<String, String> filters) {
        List<City> cities = cityRepository.findAll();
        List<CityResponseDTO> cityResponseDTOS = objectMapper.toCityResponseDTOs(cities);

        if (filters == null || filters.isEmpty()) {
            return cityResponseDTOS;
        }

        if (filters.containsKey("airports")) {
            String airportName = filters.get("airports");
            return cityResponseDTOS.stream()
                    .filter(city -> city.getAirports().stream()
                            .anyMatch(airport -> airport.getName().toLowerCase().contains(airportName)))
                    .toList();
        }
        if (filters.containsKey("ports")) {
            String portName = filters.get("ports");
            return cityResponseDTOS.stream()
                    .filter(city -> city.getPorts().stream()
                            .anyMatch(port -> port.getName().toLowerCase().contains(portName)))
                    .toList();
        }

        try {
            Specification<City> citySpecification = specificationBuilder.createFilterSpecifications(filters);
            return objectMapper.toCityResponseDTOs(cityRepository.findAll(citySpecification));
        }catch (PathElementException e){
            throw new ResourceNotFoundException("No such filter key found : " + e.getMessage());
        }
    }

    /**
     * Generates a unique city code by combining the district code and province code
     * provided in the input data. The generation logic is handled by the numberService.
     *
     * @param data a HashMap containing the keys "districtCode" and "provinceCode",
     *             which are used to generate the city code
     * @return the generated unique city code as a String
     * @throws IllegalArgumentException if either "districtCode" or "provinceCode"
     *                                  is missing in the input data
     */
    @Transactional(readOnly = true)
    @Override
    public String getCityCode(HashMap<String, String> data) {
        String districtCode = data.get("districtCode");
        String provinceCode = data.get("provinceCode");
        return numberService.generateCityCode(districtCode, provinceCode);
    }

    /**
     * Retrieves a list of all cities.
     *
     * @return a list of CityListDTO objects representing all cities
     */
    @Transactional(readOnly = true)
    @Override
    public List<CityListDTO> getAllCityList() {
        return objectMapper.toCityListDTOs(cityRepository.findAll());
    }

    /**
     * Saves a new city based on the provided city details.
     * The method ensures the city code is unique and handles associations with related entities (like airports and ports).
     *
     * @param cityRequestDTO an object containing the city details to be saved
     * @return the saved {@link City} entity
     * @throws ResourceAlreadyExistException if a city with the provided code already exists
     * @throws TransactionRollbackException if a database operation fails during the save process
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public City saveCity(CityRequestDTO cityRequestDTO) {

        if (cityRepository.existsByCode(cityRequestDTO.getCode())) {
            throw new ResourceAlreadyExistException("City with code " + cityRequestDTO.getCode() + " already exists");
        }

        try {
            City cityEntity = objectMapper.toCityEntity(cityRequestDTO);

            Optional.ofNullable(cityEntity.getAirports())
                    .ifPresent(airports -> airports.forEach(airport ->
                            airport.setCity(cityEntity)));

            Optional.ofNullable(cityEntity.getPorts())
                    .ifPresent(ports -> ports.forEach(port ->
                            port.setCity(cityEntity)));

            return cityRepository.save(cityEntity);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Saving City Data", e);
        }

    }

    /**
     * Updates an existing city based on the provided {@link CityRequestDTO}.
     *
     * The method checks if a city with the specified code exists before updating.
     * It also ensures uniqueness of the city code and updates associated data such as locations, airports,
     * and ports, which are cleared and re-assigned if provided in the input.
     *
     * @param cityRequestDTO the data transfer object containing updated city information
     * @return the updated City entity after persisting changes to the database
     * @throws ResourceNotFoundException if a city with the specified code does not exist
     * @throws ResourceAlreadyExistException if the new code provided conflicts with an existing code in another city
     * @throws TransactionRollbackException if an error occurs during the database transaction
     */
    @Transactional(rollbackFor = TransactionRollbackException.class)
    @Override
    public City updateCity(CityRequestDTO cityRequestDTO) {

        City existingcity = cityRepository.findByCode(cityRequestDTO.getCode())
                .orElseThrow(() -> new ResourceNotFoundException("City with code " + cityRequestDTO.getCode() + " not found"));

        if (!existingcity.getCode().equals(cityRequestDTO.getCode())
                && cityRepository.existsByCodeAndIdNot(cityRequestDTO.getCode(), existingcity.getId())) {
            throw new ResourceAlreadyExistException("City with code " + cityRequestDTO.getCode() + " already exists");
        }

        try {
            City cityEntity = objectMapper.toCityEntity(cityRequestDTO);

            existingcity.getLocations().clear();
            Optional.ofNullable(cityEntity.getLocations())
                    .ifPresent(locations -> locations.forEach(location -> {
                        location.setCity(existingcity);
                        existingcity.getLocations().add(location);
                    }));

            existingcity.getAirports().clear();
            Optional.ofNullable(cityEntity.getAirports())
                    .ifPresent(airports -> airports.forEach(airport -> {
                        airport.setCity(existingcity);
                        existingcity.getAirports().add(airport);
                    }));

            existingcity.getPorts().clear();
            Optional.ofNullable(cityEntity.getPorts())
                    .ifPresent(ports -> ports.forEach(port -> {
                        port.setCity(existingcity);
                        existingcity.getPorts().add(port);
                    }));

            BeanUtils.copyProperties(cityEntity, existingcity, "id", "airports", "ports", "locations", "createdon", "updatedon");
            return cityRepository.save(existingcity);
        } catch (Exception e) {
            throw new TransactionRollbackException("Database operation failed", "Updating City Data", e);
        }

    }

    /**
     * Deletes a city based on the provided city ID. Throws a ResourceNotFoundException
     * if the city with the given ID does not exist.
     *
     * @param id the ID of the city to be deleted
     */
    @Override
    public void deleteCity(Integer id) {
        try {
            cityRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("City with id " + id + " not found"));
            cityRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ForeignKeyConstraintViolationException("Cannot delete city: Caused by having multiple associations");
        }
    }
}
