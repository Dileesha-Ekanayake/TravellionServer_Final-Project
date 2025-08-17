package lk.travel.travellion.service.generic;

import lk.travel.travellion.dto.genericdto.GenericRequestDTO;
import lk.travel.travellion.dto.genericdto.GenericResponseDTO;
import lk.travel.travellion.dto.genericdto.GenericSearchDTO;
import lk.travel.travellion.entity.Generic;

import java.util.HashMap;
import java.util.List;

public interface GenericService {
    
    List<GenericResponseDTO> getAllGenerics(HashMap<String, String> filters);

    List<GenericResponseDTO> searchGenerics(GenericSearchDTO genericSearchDTO);
    
    String getGenericReferenceNumber();

    Generic saveGeneric(GenericRequestDTO genericRequestDTO);

    Generic updateGeneric(GenericRequestDTO genericRequestDTO);

    void deleteGeneric(Integer id);
}
