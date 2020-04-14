package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CustomerDTO;
import guru.springframework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springframework.TestUtils.*;
import static guru.springframework.controllers.v1.CustomerController.URL_CUSTOMERS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomersTest() throws Exception {
        // Given
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(ID1)
                .firstName(NAME1)
                .lastName(LAST_NAME1)
                .build();
        CustomerDTO customer2 = CustomerDTO.builder()
                .id(ID2)
                .firstName(NAME2)
                .lastName(LAST_NAME2)
                .build();
        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);
        when(customerService.getAllCustomers()).thenReturn(customers);

        // When
        mockMvc.perform(get(URL_CUSTOMERS).contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        // Given
        CustomerDTO customer = CustomerDTO.builder()
                .id(ID1)
                .firstName(NAME1)
                .lastName(LAST_NAME1)
                .build();
        when(customerService.getCustomerById(ID1)).thenReturn(customer);

        // When
        mockMvc.perform(get(URL_CUSTOMERS + "/" + ID1).contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(NAME1)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME1)));
    }
}
