package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static guru.springframework.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private static final Customer CUSTOMER = Customer.builder()
            .id(ID1)
            .firstName(NAME1)
            .lastName(LAST_NAME1)
            .build();
    private static final CustomerDTO CUSTOMER_DTO = CustomerDTO.builder()
            .id(ID1)
            .firstName(NAME1)
            .lastName(LAST_NAME1)
            .build();

    @Mock
    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    void getAllCustomersTest() {
        // Given
        List<Customer> customers = Arrays.asList(CUSTOMER, CUSTOMER, CUSTOMER);
        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        // Then
        assertEquals(3, customerDTOS.size());
    }

    @Test
    void getCustomerByIdTest() {
        // Given
        when(customerRepository.findById(ID1)).thenReturn(Optional.of(CUSTOMER));

        // When
        CustomerDTO customerDTO = customerService.getCustomerById(ID1);

        // Then
        assertCustomerDTO(customerDTO);
    }

    @Test
    void getCustomerByIdNotFoundTest() {
        // Given
        when(customerRepository.findById(ID1)).thenReturn(Optional.empty());

        // When Then throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(ID1));
    }

    @Test
    void createCustomerTest() {
        // Given
        when(customerRepository.save(any(Customer.class))).thenReturn(CUSTOMER);

        // When
        CustomerDTO customerDTO = customerService.createCustomer(CUSTOMER_DTO);

        // Then
        assertCustomerDTO(customerDTO);
    }

    @Test
    void saveCustomerTest() {
        // Given
        when(customerRepository.findById(ID1)).thenReturn(Optional.of(new Customer()));
        when(customerRepository.save(any(Customer.class))).thenReturn(CUSTOMER);

        // When
        CustomerDTO customerDTO = customerService.saveCustomer(ID1, CUSTOMER_DTO);

        // Then
        assertCustomerDTO(customerDTO);
    }

    @Test
    void saveCustomerNotFoundTest() {
        // Given
        when(customerRepository.findById(ID1)).thenReturn(Optional.empty());

        // When Then throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            customerService.saveCustomer(ID1, CUSTOMER_DTO);
        });
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void updateCustomerTest() {
        // Given
        when(customerRepository.findById(ID1)).thenReturn(Optional.of(CUSTOMER));
        when(customerRepository.save(any(Customer.class))).thenReturn(CUSTOMER);

        // When
        CustomerDTO customerDTO = customerService.updateCustomer(ID1, CUSTOMER_DTO);

        // Then
        assertCustomerDTO(customerDTO);
    }

    @Test
    void updateCustomerNotFoundTest() {
        // Given
        when(customerRepository.findById(ID1)).thenReturn(Optional.empty());

        // When Then throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            customerService.updateCustomer(ID1, CUSTOMER_DTO);
        });
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void deleteCustomerByIdTest() {
        // Given
        when(customerRepository.findById(ID1)).thenReturn(Optional.of(new Customer()));

        // When
        customerService.deleteCustomerById(ID1);

        // Then
        verify(customerRepository).deleteById(ID1);
    }

    @Test
    void deleteCustomerByIdNotFoundTest() {
        // Given
        when(customerRepository.findById(ID1)).thenReturn(Optional.empty());

        // When Then throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> {
            customerService.deleteCustomerById(ID1);
        });
        verify(customerRepository, never()).deleteById(ID1);
    }

    private void assertCustomerDTO(CustomerDTO customerDTO) {
        assertNotNull(customerDTO);
        assertEquals(ID1, customerDTO.getId());
        assertEquals(NAME1, customerDTO.getFirstName());
        assertEquals(LAST_NAME1, customerDTO.getLastName());
        assertEquals(CUSTOMER_URL, customerDTO.getCustomerUrl());
    }
}
