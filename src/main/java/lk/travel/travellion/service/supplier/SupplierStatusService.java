package lk.travel.travellion.service.supplier;

import lk.travel.travellion.dto.supplierdto.SupplierstatusDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.SupplierStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierStatusService {

    private final SupplierStatusRepository supplierStatusRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<SupplierstatusDTO> getAllSupplierStatus() {
        return objectMapper.toSupplierStatusDTOs(supplierStatusRepository.findAll());
    }
}
