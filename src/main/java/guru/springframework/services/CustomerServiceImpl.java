package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static guru.springframework.controllers.v1.CustomerController.URL_CUSTOMERS;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@RequiredArgsConstructor
@Service
final class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::getCustomerWithUrl)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(this::getCustomerWithUrl)
                // TODO Handle NotFoundException
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.save(
                customerMapper.customerDTOToCustomer(customerDTO));
        return getCustomerWithUrl(customer);
    }

    private CustomerDTO getCustomerWithUrl(Customer customer) {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setCustomerUrl(URL_CUSTOMERS + "/" + customer.getId());
        return customerDTO;
    }
}
