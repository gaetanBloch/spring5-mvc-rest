package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.controllers.RestResponseEntityExceptionHandler;
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

import static guru.springframework.TestUtils.NAME1;
import static guru.springframework.TestUtils.VENDOR_URL;
import static guru.springframework.controllers.v1.VendorController.URL_VENDORS;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
