package guru.springframework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
@Data
@AllArgsConstructor
public class CategoryListDTO {
    List<CategoryDTO> categories;
}
