package lk.travel.travellion.service.tour;

import lk.travel.travellion.dto.tourcontractdto.TourcontractRequestDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractResponseDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractSearchDTO;
import lk.travel.travellion.dto.tourcontractdto.TourcontractViewResponseDTO;
import lk.travel.travellion.entity.Tourcontract;

import java.util.HashMap;
import java.util.List;

public interface TourContractService {
    List<TourcontractResponseDTO> getAllTourContracts(HashMap<String, String> filters);

    List<TourcontractResponseDTO> searchTours(TourcontractSearchDTO tourcontractSearchDTO);

    TourcontractViewResponseDTO getTourContractView(String tourReference);

    String getTourContractReference();

    Tourcontract saveTourContract(TourcontractRequestDTO tourcontractRequestDTO);

    Tourcontract updateTourContract(TourcontractRequestDTO tourcontractRequestDTO);

    void deleteTourContract(Integer id);
}
