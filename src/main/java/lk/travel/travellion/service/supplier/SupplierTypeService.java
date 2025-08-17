package lk.travel.travellion.service.supplier;

import lk.travel.travellion.dto.supplierdto.SuppliertypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.SupplierTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierTypeService {

    private final SupplierTypeRepository supplierTypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<SuppliertypeDTO> getAllSupplierTypes() {
        return objectMapper.toSupplierTypeDTOs(supplierTypeRepository.findAll());
    }
}
