package guru.springframework.services;

import guru.springframework.api.v1.mapper.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static guru.springframework.TestUtils.ID1;
import static guru.springframework.TestUtils.NAME1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository, categoryMapper);
    }

    @Test
    void getAllCategoriesTest() {
        // Given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(categoryRepository.findAll()).thenReturn(categories);

        // When
        List<CategoryDTO> categoryDTOS = categoryService.getAllCategories();

        // Then
        assertEquals(3, categoryDTOS.size());
    }

    @Test
    void getCategoryByNameTest() {
        // Given
        Category category = Category.builder().id(ID1).name(NAME1).build();
        when(categoryRepository.findByName(NAME1)).thenReturn(category);

        // When
        CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME1);

        // Then
        assertNotNull(categoryDTO);
        assertEquals(ID1, categoryDTO.getId());
        assertEquals(NAME1, categoryDTO.getName());
    }
}
