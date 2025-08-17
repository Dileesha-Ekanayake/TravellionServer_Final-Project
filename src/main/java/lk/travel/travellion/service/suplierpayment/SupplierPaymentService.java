package lk.travel.travellion.service.suplierpayment;

import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentInfoDTO;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentRequestDTO;
import lk.travel.travellion.dto.supplierpaymentdto.SupplierpaymentResponseDTO;
import lk.travel.travellion.entity.Supplierpayment;

import java.util.HashMap;
import java.util.List;

public interface SupplierPaymentService {

    List<SupplierpaymentResponseDTO> getAllSupplierPayments(HashMap<String, String> filters);

    String getSupplierPaymentCode(String supplierCode);

    SupplierpaymentInfoDTO getSupplierPaymentInfo(String supplierCode);

    Supplierpayment saveSupplierPayment(SupplierpaymentRequestDTO supplierpaymentRequestDTO);

    Supplierpayment updateSupplierPayment(SupplierpaymentRequestDTO supplierpaymentRequestDTO);

    void deleteSupplierPayment(Integer id);
}
