package guru.springframework.api.v1.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
@Data
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
}
