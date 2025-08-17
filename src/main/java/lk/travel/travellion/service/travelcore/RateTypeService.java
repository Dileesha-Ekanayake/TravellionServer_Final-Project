package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.RatetypeDTO;
import lk.travel.travellion.entity.Ratetype;

import java.util.List;

public interface RateTypeService {
    
    List<RatetypeDTO> getAllRateTypes();

    Ratetype saveRateType(RatetypeDTO ratetypeDTO);

    Ratetype updateRateType(RatetypeDTO ratetypeDTO);

    void deleteRateType(Integer id);
}
