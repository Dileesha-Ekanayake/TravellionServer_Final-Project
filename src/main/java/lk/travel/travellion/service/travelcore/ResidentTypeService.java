package lk.travel.travellion.service.travelcore;

import lk.travel.travellion.dto.setupdetailsdto.ResidenttypeDTO;
import lk.travel.travellion.entity.Residenttype;

import java.util.List;

public interface ResidentTypeService {
    
    List<ResidenttypeDTO> getAllResidentTypes();

    Residenttype saveResidentType(ResidenttypeDTO residenttypeDTO);

    Residenttype updateResidentType(ResidenttypeDTO residenttypeDTO);

    void deleteResidentType(Integer id);
}
