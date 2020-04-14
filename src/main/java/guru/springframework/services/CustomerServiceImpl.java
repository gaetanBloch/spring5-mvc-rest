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
                .orElseThrow(() -> new RuntimeException("Customer not found for id = " + id));
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.save(
                customerMapper.customerDTOToCustomer(customerDTO));
        return getCustomerWithUrl(customer);
    }

    @Override
    public CustomerDTO saveCustomer(Long id, CustomerDTO customerDTO) {
        if (customerRepository.findById(id).isEmpty()) {
            // TODO Handle NotFoundException
            throw new RuntimeException("Customer not found for id = " + id);
        }

        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
        return getCustomerWithUrl(customerRepository.save(customer));
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    if (customerDTO.getFirstName() != null) {
                        customer.setFirstName(customerDTO.getFirstName());
                    }
                    if (customerDTO.getLastName() != null) {
                        customer.setLastName(customerDTO.getLastName());
                    }
                    return getCustomerWithUrl(customerRepository.save(customer));
                })
                // TODO Handle NotFoundException
                .orElseThrow(() -> new RuntimeException("Customer not found for id = " + id));
    }

    @Override
    public void deleteCustomerById(Long id) {
        if (customerRepository.findById(id).isEmpty()) {
            // TODO Handle NotFoundException
            throw new RuntimeException("Customer not found for id = " + id);
        }

        customerRepository.deleteById(id);
    }

    private CustomerDTO getCustomerWithUrl(Customer customer) {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setCustomerUrl(URL_CUSTOMERS + "/" + customer.getId());
        return customerDTO;
    }
}
