package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.bootstrap.Bootstrap;
import guru.springframework.domain.Vendor;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static guru.springframework.TestUtils.NAME1;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VendorServiceIT {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private VendorRepository vendorRepository;
    private VendorMapper vendorMapper = VendorMapper.INSTANCE;
    private VendorService vendorService;

    @BeforeEach
    void setUp() {
        // Bootstrap Data for testing purpose
        new Bootstrap(categoryRepository, customerRepository, vendorRepository).run();
        System.out.println("Loaded Vendor Data, count = " + vendorRepository.count());

        vendorService = new VendorServiceImpl(vendorRepository, vendorMapper);
    }

    @Test
    void saveCustomer() {
        // Given
        Long id = getVendorId();
        Vendor originalVendor = vendorRepository.getOne(id);
        String originalName = originalVendor.getName();
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME1).build();

        // When
        vendorService.saveVendor(id, vendorDTO);
        Optional<Vendor> updatedVendor = vendorRepository.findById(id);

        // Then
        assertTrue(updatedVendor.isPresent());
        assertEquals(NAME1, updatedVendor.get().getName());
        assertNotEquals(originalName, updatedVendor.get().getName());
    }

    @Test
    void updateCustomerName() {
        // Given
        Long id = getVendorId();
        Vendor originalVendor = vendorRepository.getOne(id);
        String originalName = originalVendor.getName();
        VendorDTO vendorDTO = VendorDTO.builder().name(NAME1).build();

        // When
        vendorService.updateVendor(id, vendorDTO);
        Optional<Vendor> updatedVendor = vendorRepository.findById(id);

        // Then
        assertTrue(updatedVendor.isPresent());
        assertEquals(NAME1, updatedVendor.get().getName());
        assertNotEquals(originalName, updatedVendor.get().getName());
    }

    private Long getVendorId() {
        return vendorRepository.findAll().get(0).getId();
    }
}
