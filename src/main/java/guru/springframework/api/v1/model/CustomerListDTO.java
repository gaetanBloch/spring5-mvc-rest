package guru.springframework.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Gaetan Bloch
 * Created on 14/04/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerListDTO {
    private List<CategoryDTO> categories;
}
