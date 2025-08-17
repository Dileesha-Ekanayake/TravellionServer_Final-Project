package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.CurrencyDTO;
import lk.travel.travellion.entity.Currency;

import java.util.List;

public interface CurrencyService {
    
    List<CurrencyDTO> getAllCurrencies();

    Currency saveCurrency(CurrencyDTO currencyDTO);

    Currency updateCurrency(CurrencyDTO currencyDTO);

    void deleteCurrency(Integer id);
}
