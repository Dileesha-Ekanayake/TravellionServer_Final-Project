package lk.travel.travellion.service.supplier;

import lk.travel.travellion.dto.supplierdto.SupplierListDTO;
import lk.travel.travellion.dto.supplierdto.SupplierRequestDTO;
import lk.travel.travellion.dto.supplierdto.SupplierResponseDTO;
import lk.travel.travellion.entity.Supplier;

import java.util.HashMap;
import java.util.List;

public interface SupplierService {
   
    List<SupplierResponseDTO> getAllSuppliers(HashMap<String, String> filters);

    List<SupplierListDTO> getAllSupplierList();

    List<SupplierListDTO> getAllAccommSupplierList();

    List<SupplierListDTO> getAllTransferSupplierList();

    List<SupplierListDTO> getAllGenericSupplierList();

    String getSupplierBrNo();

    Supplier saveSupplier(SupplierRequestDTO supplierRequestDTO);

    Supplier updateSupplier(SupplierRequestDTO supplierRequestDTO);

    void deleteSupplier(Integer id);
}
