package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static guru.springframework.TestUtils.ID1;
import static guru.springframework.TestUtils.NAME1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Gaetan Bloch
 * Created on 15/04/2020
 */
@ExtendWith(MockitoExtension.class)
class VendorServiceTest {

    private static final Vendor VENDOR = Vendor.builder().id(ID1).name(NAME1).build();

    @Mock
    private VendorRepository vendorRepository;
    private VendorMapper vendorMapper = VendorMapper.INSTANCE;
    private VendorService vendorService;

    @BeforeEach
    void setUp() {
        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    void getAllVendorsTest() {
        // Given
        List<Vendor> vendors = Arrays.asList(VENDOR, VENDOR, VENDOR);
        when(vendorRepository.findAll()).thenReturn(vendors);

        // When
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        // Then
        assertEquals(3, vendorDTOS.size());
    }
}
