package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.CancellationschemeDTO;
import lk.travel.travellion.entity.Cancellationscheme;

import java.util.List;

public interface CancellationSchemeService {
    
    List<CancellationschemeDTO> getAllCancellationSchemes();

    Cancellationscheme saveCancellationScheme(CancellationschemeDTO cancellationschemeDTO);

    Cancellationscheme updateCancellationScheme(CancellationschemeDTO cancellationschemeDTO);

    void deleteCancellationScheme(Integer id);
}
