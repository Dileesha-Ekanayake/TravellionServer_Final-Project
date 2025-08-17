package lk.travel.travellion.service.customerpayment;

import lk.travel.travellion.dto.customerpaymentdto.CustomerpaymentRequestDTO;
import lk.travel.travellion.dto.customerpaymentdto.CustomerpaymentResponseDTO;
import lk.travel.travellion.entity.Customerpayment;

import java.util.HashMap;
import java.util.List;

public interface CustomerPaymentService {

    List<CustomerpaymentResponseDTO> getAllCustomerPayments(HashMap<String, String> filters);

    String getCustomerPaymentCode(String customerCode);

    Customerpayment saveCustomerPayment(CustomerpaymentRequestDTO customerpaymentRequestDTO);

    Customerpayment updateCustomerPayment(CustomerpaymentRequestDTO customerpaymentRequestDTO);

    void deleteCustomerPayment(Integer id);
}
