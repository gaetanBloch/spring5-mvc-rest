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
                .map(this::getVendorWithUrl)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(this::getVendorWithUrl)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Vendor Not Found for id = " + id);
                });
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorRepository.save(
                vendorMapper.vendorDTOtoVendor(vendorDTO)
        );
        return getVendorWithUrl(vendor);
    }

    @Override
    public VendorDTO saveVendor(Long id, VendorDTO vendorDTO) {
        if (vendorRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Vendor Not Found for id = " + id);
        }

        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        vendor.setId(id);
        return getVendorWithUrl(vendorRepository.save(vendor));
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if (vendorDTO.getName() != null) {
                        vendor.setName(vendorDTO.getName());
                    }
                    return getVendorWithUrl(vendorRepository.save(vendor));
                })
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException("Vendor Not Found for id = " + id);
                });
    }

    @Override
    public void deleteVendorById(Long id) {
        if (vendorRepository.findById(id).isEmpty()) {
            throw new ResourceNotFoundException("Vendor Not Found for id = " + id);
        }

        vendorRepository.deleteById(id);
    }

    private VendorDTO getVendorWithUrl(Vendor vendor) {
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        vendorDTO.setVendorUrl(URL_VENDORS + "/" + vendor.getId());
        return vendorDTO;
    }
}
