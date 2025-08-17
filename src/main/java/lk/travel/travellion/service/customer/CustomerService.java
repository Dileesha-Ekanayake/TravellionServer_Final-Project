package lk.travel.travellion.service.customer;

import lk.travel.travellion.dto.customerdto.CustomerListDTO;
import lk.travel.travellion.dto.customerdto.CustomerRequestDTO;
import lk.travel.travellion.dto.customerdto.CustomerResponseDTO;
import lk.travel.travellion.entity.Customer;

import java.util.HashMap;
import java.util.List;

public interface CustomerService {

    List<CustomerResponseDTO> getAllCustomers(HashMap<String, String> filters);

    List<CustomerListDTO> getAllCustomerList();

    String getCustomerCode();

    Customer saveCustomer(CustomerRequestDTO customer);

    Customer updateCustomer(CustomerRequestDTO customer);

    void deleteCustomer(Integer id);

}
