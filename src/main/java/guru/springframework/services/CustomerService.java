package guru.springframework.services;

import guru.springframework.api.v1.model.CustomerDTO;

import java.util.List;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(Long id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomer(Long id, CustomerDTO customerDTO);

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);
}
