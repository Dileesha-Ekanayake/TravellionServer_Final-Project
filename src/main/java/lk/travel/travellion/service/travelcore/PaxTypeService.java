package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.PaxtypeDTO;
import lk.travel.travellion.entity.Paxtype;

import java.util.List;

public interface PaxTypeService {
    
    List<PaxtypeDTO> getAllPaxTypes();

    Paxtype savePaxType(PaxtypeDTO paxtypeDTO);

    Paxtype updatePaxType(PaxtypeDTO paxtypeDTO);

    void deletePaxType(Integer id);
}
