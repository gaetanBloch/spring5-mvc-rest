package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Gaetan Bloch
 * Created on 15/04/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorDTO {
    private String name;
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
