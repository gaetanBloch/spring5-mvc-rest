package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import org.junit.jupiter.api.Test;

import static guru.springframework.TestUtils.ID1;
import static guru.springframework.TestUtils.NAME1;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Gaetan Bloch
 * Created on 15/04/2020
 */
class VendorMapperTest {

    private VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDTOTest() {
        // Given
        Vendor vendor = Vendor.builder().id(ID1).name(NAME1).build();

        // When
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        // Then
        assertNotNull(vendorDTO);
        assertEquals(NAME1, vendorDTO.getName());
        assertNull(vendorDTO.getVendorUrl());
    }

    @Test
    void vendorDTOtoVendorTest() {
        // Given
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME1).build();

        // When
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);

        // Then
        assertNotNull(vendor);
        assertEquals(NAME1, vendor.getName());
    }
}
