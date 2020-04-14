package guru.springframework.api.v1.model;

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
    private String firstName;
    private String lastName;
    private String customerUrl;
}
