package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.api.v1.model.CategoryListDTO;
import guru.springframework.services.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
@Api(description = "This is the Category REST API Documentation")
@RequiredArgsConstructor
@RestController
@RequestMapping(CategoryController.URL_CATEGORIES)
final class CategoryController {
    static final String URL_CATEGORIES = "/api/v1/categories";

    private final CategoryService categoryService;

    @ApiOperation(value = "This will get the list of Categories")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CategoryListDTO getAllCategories() {
        return new CategoryListDTO(categoryService.getAllCategories());

    }

    @ApiOperation(value = "This will get a single Category")
    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }
}
