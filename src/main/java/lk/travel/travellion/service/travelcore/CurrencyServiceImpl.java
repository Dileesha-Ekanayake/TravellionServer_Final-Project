package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.CurrencyDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.entity.Currency;
import lk.travel.travellion.exceptions.ResourceAlreadyExistException;
import lk.travel.travellion.exceptions.ResourceNotFoundException;
import lk.travel.travellion.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final ObjectMapper objectMapper;

    /**
     * Retrieves a list of all available currencies.
     *
     * @return a list of {@link CurrencyDTO} objects representing all currencies.
     */
    @Transactional(readOnly = true)
    @Override
    public List<CurrencyDTO> getAllCurrencies() {
        return objectMapper.toCurrencyDTOs(currencyRepository.findAll());
    }

    /**
     * Saves a new currency entity in the database based on the provided CurrencyDTO.
     * If a currency with the same name already exists, a ResourceAlreadyExistException is thrown.
     *
     * @param currencyDTO the CurrencyDTO object containing the details of the currency to be saved
     * @return the saved Currency entity
     * @throws ResourceAlreadyExistException if a currency with the same name already exists
     */
    @Override
    public Currency saveCurrency(CurrencyDTO currencyDTO) {

        if (currencyRepository.existsByName(currencyDTO.getName())) {
            throw new ResourceAlreadyExistException("Currency with name " + currencyDTO.getName() + " already exists");
        }
        return currencyRepository.save(objectMapper.toCurrencyEntity(currencyDTO));
    }

    /**
     * Updates an existing currency entity with the information provided in the given {@code CurrencyDTO}.
     * If the currency with the specified ID does not exist, a {@code ResourceNotFoundException} is thrown.
     * If a currency with the same name (but a different ID) already exists, a {@code ResourceAlreadyExistException} is thrown.
     *
     * @param currencyDTO the data transfer object containing the details of the currency to update
     * @return the updated {@code Currency} entity
     * @throws ResourceNotFoundException if no currency is found with the specified ID
     * @throws ResourceAlreadyExistException if a currency with the same name (different ID) already exists
     */
    @Override
    public Currency updateCurrency(CurrencyDTO currencyDTO) {
        Currency existingCurrency = currencyRepository.findById(currencyDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Currency with id " + currencyDTO.getId() + " not found"));

        if (!existingCurrency.getName().equals(currencyDTO.getName()) &&
            currencyRepository.existsByNameAndIdNot(currencyDTO.getName(), existingCurrency.getId())) {
            throw new ResourceAlreadyExistException("Currency with name " + currencyDTO.getName() + " already exists");
        }

        return currencyRepository.save(objectMapper.toCurrencyEntity(currencyDTO));
    }

    /**
     * Deletes an existing currency identified by its unique ID. If the currency with the specified ID
     * does not exist, a {@code ResourceNotFoundException} is thrown.
     *
     * @param id the unique identifier of the currency to be deleted
     * @throws ResourceNotFoundException if no currency is found with the provided ID
     */
    @Override
    public void deleteCurrency(Integer id) {
        currencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Currency with id " + id + " not found"));
        currencyRepository.deleteById(id);
    }
}
