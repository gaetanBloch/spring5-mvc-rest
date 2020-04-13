package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(CategoryController.URL_CATEGORIES)
public class CategoryController {
    static final String URL_CATEGORIES = "/api/v1/categories/";

    private final CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryListDTO> getAllCategories() {
        return new ResponseEntity<>(
                new CategoryListDTO(categoryService.getAllCategories()),
                HttpStatus.OK
        );
    }

    @GetMapping(path = "{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(
                categoryService.getCategoryByName(name),
                HttpStatus.OK
        );
    }
}
