package guru.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    private Long id;
    @ApiModelProperty(value = "First Name", required = true)
    @JsonProperty("first_name")
    private String firstName;
    @ApiModelProperty(value = "Last Name", required = true)
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("customer_url")
    private String customerUrl;
}
