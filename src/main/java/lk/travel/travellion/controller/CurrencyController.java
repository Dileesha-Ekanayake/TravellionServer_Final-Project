package lk.travel.travellion.controller;

import jakarta.validation.Valid;
import lk.travel.travellion.apiresponse.ApiResponse;
import lk.travel.travellion.dto.setupdetailsdto.CurrencyDTO;
import lk.travel.travellion.entity.Currency;
import lk.travel.travellion.service.travelcore.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<List<CurrencyDTO>>> getCurrencies() {
        List<CurrencyDTO> currencies = currencyService.getAllCurrencies();
        return ApiResponse.successResponse(currencies);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@Valid @RequestBody CurrencyDTO currencyDTO) {
        Currency savedCurrency = currencyService.saveCurrency(currencyDTO);
        return ApiResponse.successResponse(
                "Successfully Saved",
                HttpStatus.CREATED,
                "Currency : " + savedCurrency.getName()
        );
    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@Valid @RequestBody CurrencyDTO currencyDTO) {
        Currency updateCurrency = currencyService.updateCurrency(currencyDTO);
        return ApiResponse.successResponse(
                "Successfully Updated",
                HttpStatus.OK,
                "Currency : " + updateCurrency.getName()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable int id) {
        currencyService.deleteCurrency(id);
        return ApiResponse.successResponse(
                "Successfully Deleted",
                HttpStatus.OK,
                "Currency ID : " + id
        );
    }
}
