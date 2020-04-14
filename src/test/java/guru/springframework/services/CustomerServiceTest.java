package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
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
import static org.mockito.Mockito.when;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

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
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        // Then
        assertEquals(3, customerDTOS.size());
    }

    @Test
    void getCustomerByIdTest() {
        // Given
        Customer customer = Customer.builder().id(ID1).firstName(NAME1).lastName(LAST_NAME1).build();
        when(customerRepository.findById(ID1)).thenReturn(Optional.of(customer));

        // When
        CustomerDTO customerDTO = customerService.getCustomerById(ID1);

        // Then
        assertNotNull(customerDTO);
        assertEquals(ID1, customerDTO.getId());
        assertEquals(NAME1, customerDTO.getFirstName());
        assertEquals(LAST_NAME1, customerDTO.getLastName());
        assertEquals(CUSTOMER_URL, customerDTO.getCustomerUrl());
    }

    @Test
    void getCustomerByIdNotFoundTest() {
        // Given
        when(customerRepository.findById(ID1)).thenReturn(Optional.empty());

        // When Then throws Exception
        assertThrows(RuntimeException.class, () -> customerService.getCustomerById(ID1));
    }
}
