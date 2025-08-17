package lk.travel.travellion.service.suplierpayment;

import lk.travel.travellion.dto.supplierpaymentdto.PaymentstatusDTO;
import lk.travel.travellion.dtomapper.ObjectMapper;
import lk.travel.travellion.repository.PaymentstatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierPaymentStatusService {

    private final PaymentstatusRepository paymentstatusRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public List<PaymentstatusDTO> getAllSupplierPaymentStatus() {
        return objectMapper.toPaymentStatusDTOs(paymentstatusRepository.findAll());
    }
}
