package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.domain.Customer;
import org.junit.jupiter.api.Test;

import static guru.springframework.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
class CustomerMapperTest {

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    void customerToCustomerDTOTest() {
        // Given
        Customer customer = Customer.builder()
                .id(ID1)
                .firstName(NAME1)
                .lastName(LAST_NAME1)
                .build();

        // When
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        // Then
        assertNotNull(customerDTO);
        assertEquals(ID1, customerDTO.getId());
        assertEquals(NAME1, customerDTO.getFirstName());
        assertEquals(LAST_NAME1, customerDTO.getLastName());
        assertNull(customerDTO.getCustomerUrl());
    }
}
