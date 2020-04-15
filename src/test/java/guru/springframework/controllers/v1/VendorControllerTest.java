package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.RestResponseEntityExceptionHandler;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.services.VendorService;
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
import static guru.springframework.controllers.v1.VendorController.URL_VENDORS;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Gaetan Bloch
 * Created on 15/04/2020
 */
@ExtendWith(MockitoExtension.class)
class VendorControllerTest extends AbstractControllerTest {

    private static final VendorDTO VENDOR_DTO = VendorDTO.builder()
            .name(NAME1)
            .vendorUrl(VENDOR_URL)
            .build();

    @Mock
    private VendorService vendorService;
    @InjectMocks
    private VendorController vendorController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    void getAllVendors() throws Exception {
        // Given
        List<VendorDTO> vendors = Arrays.asList(VENDOR_DTO, VENDOR_DTO);
        when(vendorService.getAllVendors()).thenReturn(vendors);

        // When
        mockMvc.perform(get(URL_VENDORS).contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        // Given
        when(vendorService.getVendorById(ID1)).thenReturn(VENDOR_DTO);

        // When
        mockMvc.perform(get(URL_VENDORS + "/" + ID1)
                .contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME1)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL)));
    }

    @Test
    void getCustomerByIdNotFoundTest() throws Exception {
        // Given
        when(vendorService.getVendorById(ID1)).thenThrow(ResourceNotFoundException.class);

        // When
        mockMvc.perform(get(URL_VENDORS + "/" + ID1)
                .contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    void createCustomerTest() throws Exception {
        // Given
        when(vendorService.createVendor(any(VendorDTO.class))).thenReturn(VENDOR_DTO);

        // When
        mockMvc.perform(post(URL_VENDORS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(VENDOR_DTO)))

                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME1)))
                .andExpect(jsonPath("$.vendor_url", equalTo(VENDOR_URL)));
    }
}
