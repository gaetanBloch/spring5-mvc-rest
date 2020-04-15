package guru.springframework.services;

import guru.springframework.api.v1.mapper.VendorMapper;
import guru.springframework.api.v1.model.VendorDTO;
import guru.springframework.domain.Vendor;
import guru.springframework.exceptions.ResourceNotFoundException;
import guru.springframework.repositories.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static guru.springframework.controllers.v1.VendorController.URL_VENDORS;

/**
 * @author Gaetan Bloch
 * Created on 15/04/2020
 */
@RequiredArgsConstructor
@Service
final class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(this::getCustomerWithUrl)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(this::getCustomerWithUrl)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Vendor Not Found for id = " + id);
                });
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorRepository.save(
                vendorMapper.vendorDTOtoVendor(vendorDTO)
        );
        return getCustomerWithUrl(vendor);
    }

    private VendorDTO getCustomerWithUrl(Vendor vendor) {
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        vendorDTO.setVendorUrl(URL_VENDORS + "/" + vendor.getId());
        return vendorDTO;
    }
}
