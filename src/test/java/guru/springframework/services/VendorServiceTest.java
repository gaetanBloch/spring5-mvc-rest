package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.repositories.VendorRepository;
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
import static org.mockito.Mockito.any;
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

    @Test
    void getCustomerByIdTest() {
        // Given
        when(vendorRepository.findById(ID1)).thenReturn(Optional.of(VENDOR));

        // When
        VendorDTO vendorDTO = vendorService.getVendorById(ID1);

        // Then
        assertVendorDTO(vendorDTO);
    }

    @Test
    void getCustomerByIdNotFoundTest() {
        // Given
        when(vendorRepository.findById(ID1)).thenReturn(Optional.empty());

        // When Then throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> vendorService.getVendorById(ID1));
    }

    @Test
    void createVendorTest() {
        // Given
        when(vendorRepository.save(any(Vendor.class))).thenReturn(VENDOR);

        // When
        VendorDTO vendorDTO = vendorService.createVendor(new VendorDTO());

        // Then
        assertVendorDTO(vendorDTO);
    }

    private void assertVendorDTO(VendorDTO vendorDTO) {
        assertNotNull(vendorDTO);
        assertEquals(NAME1, vendorDTO.getName());
        assertEquals(VENDOR_URL, vendorDTO.getVendorUrl());
    }
}