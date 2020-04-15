package guru.springframework.controllers.v1;

import guru.springframework.services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gaetan Bloch
 * Created on 15/04/2020
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(VendorController.URL_VENDORS)
public class VendorController {
    public static final String URL_VENDORS = "/api/v1/vendors";

    private final VendorService vendorService;
}
