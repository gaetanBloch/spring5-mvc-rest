package guru.springframework.api.v1.model;

import lombok.*;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
}
