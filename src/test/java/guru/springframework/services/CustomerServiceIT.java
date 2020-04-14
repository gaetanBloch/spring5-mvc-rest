package guru.springframework.services;

import guru.springframework.api.v1.mapper.CustomerMapper;
import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static guru.springframework.TestUtils.LAST_NAME1;
import static guru.springframework.TestUtils.NAME1;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerServiceIT {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;
    private CustomerService customerService;

    @BeforeEach
    void setUp() throws Exception {
        // Bootstrap Data for testing purpose
        new Bootstrap(categoryRepository, customerRepository).run();
        System.out.println("Loaded Customer Data, count = " + customerRepository.count());

        customerService = new CustomerServiceImpl(customerRepository, customerMapper);
    }

    @Test
    void updateCustomerFirstName() {
        // Given
        Long id = getCustomerId();
        Customer originalCustomer = customerRepository.getOne(id);
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();
        CustomerDTO customerDTO = CustomerDTO.builder().firstName(NAME1).build();

        // When
        customerService.updateCustomer(id, customerDTO);
        Optional<Customer> updatedCustomer = customerRepository.findById(id);

        // Then
        assertTrue(updatedCustomer.isPresent());
        assertEquals(NAME1, updatedCustomer.get().getFirstName());
        assertNotEquals(originalFirstName, updatedCustomer.get().getFirstName());
        assertEquals(originalLastName, updatedCustomer.get().getLastName());
    }

    @Test
    void updateCustomerLastName() {
        // Given
        Long id = getCustomerId();
        Customer originalCustomer = customerRepository.getOne(id);
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();
        CustomerDTO customerDTO = CustomerDTO.builder().lastName(LAST_NAME1).build();

        // When
        customerService.updateCustomer(id, customerDTO);
        Optional<Customer> updatedCustomer = customerRepository.findById(id);

        // Then
        assertTrue(updatedCustomer.isPresent());
        assertEquals(LAST_NAME1, updatedCustomer.get().getLastName());
        assertEquals(originalFirstName, updatedCustomer.get().getFirstName());
        assertNotEquals(originalLastName, updatedCustomer.get().getLastName());
    }

    private Long getCustomerId() {
        return customerRepository.findAll().get(0).getId();
    }
}
