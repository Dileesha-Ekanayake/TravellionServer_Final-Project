package lk.travel.travellion.service.customerpayment;

import lk.travel.travellion.dto.customerpaymentdto.PaymenttypeDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.PaymenttypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerPaymentTypeService {

    private final PaymenttypeRepository paymenttypeRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<PaymenttypeDTO> getAllCustomerPaymentTypes() {
        return objectMapper.toPaymentTypeDTOs(paymenttypeRepository.findAll());
    }
}
