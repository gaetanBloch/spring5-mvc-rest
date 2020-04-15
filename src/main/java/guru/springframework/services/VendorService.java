package guru.springframework.services;

import guru.springframework.api.v1.model.VendorDTO;

import java.util.List;

/**
 * @author Gaetan Bloch
 * Created on 15/04/2020
 */
public interface VendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO getVendorById(Long id);

    VendorDTO createVendor(VendorDTO vendorDTO);

    VendorDTO saveVendor(Long id, VendorDTO vendorDTO);

    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);
}
