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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest extends AbstractControllerTest {

    private static final CustomerDTO CUSTOMER_DTO = CustomerDTO.builder()
            .id(ID1)
            .firstName(NAME1)
            .lastName(LAST_NAME1)
            .customerUrl(CUSTOMER_URL)
            .build();

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
        CustomerDTO customer2 = CustomerDTO.builder()
                .id(ID2)
                .firstName(NAME2)
                .lastName(LAST_NAME2)
                .build();
        List<CustomerDTO> customers = Arrays.asList(CUSTOMER_DTO, customer2);
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
        when(customerService.getCustomerById(ID1)).thenReturn(CUSTOMER_DTO);

        // When
        mockMvc.perform(get(URL_CUSTOMERS + "/" + ID1)
                .contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID1.intValue())))
                .andExpect(jsonPath("$.first_name", equalTo(NAME1)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    void createCustomerTest() throws Exception {
        // Given
        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(CUSTOMER_DTO);

        // When
        mockMvc.perform(post(URL_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(CUSTOMER_DTO)))

                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(ID1.intValue())))
                .andExpect(jsonPath("$.first_name", equalTo(NAME1)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    void saveCustomerTest() throws Exception {
        // Given
        when(customerService.saveCustomer(anyLong(), any(CustomerDTO.class)))
                .thenReturn(CUSTOMER_DTO);

        // When
        mockMvc.perform(put(URL_CUSTOMERS + "/" + ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(CUSTOMER_DTO)))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID1.intValue())))
                .andExpect(jsonPath("$.first_name", equalTo(NAME1)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    void saveNewCustomerTest() throws Exception {
        // Given
        when(customerService.getCustomerById(anyLong())).thenThrow(RuntimeException.class);
        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(CUSTOMER_DTO);

        // When
        mockMvc.perform(put(URL_CUSTOMERS + "/" + ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(CUSTOMER_DTO)))

                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(ID1.intValue())))
                .andExpect(jsonPath("$.first_name", equalTo(NAME1)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    void updateCustomerTest() throws Exception {
        // Given
        when(customerService.updateCustomer(anyLong(), any(CustomerDTO.class)))
                .thenReturn(CUSTOMER_DTO);

        // When
        mockMvc.perform(patch(URL_CUSTOMERS + "/" + ID1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(CUSTOMER_DTO)))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ID1.intValue())))
                .andExpect(jsonPath("$.first_name", equalTo(NAME1)))
                .andExpect(jsonPath("$.last_name", equalTo(LAST_NAME1)))
                .andExpect(jsonPath("$.customer_url", equalTo(CUSTOMER_URL)));
    }

    @Test
    void deleteCustomerTest() throws Exception {
        // When
        mockMvc.perform(delete(URL_CUSTOMERS + "/" + ID1)
                .contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(ID1);
    }

    @Test
    void deleteCustomerNotFoundTest() throws Exception {
        // Given
        when(customerService.getCustomerById(ID1)).thenThrow(RuntimeException.class);

        // When
        mockMvc.perform(delete(URL_CUSTOMERS + "/" + ID1)
                .contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isNoContent());

        verify(customerService, never()).deleteCustomerById(ID1);
    }
}
