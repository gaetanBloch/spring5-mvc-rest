package guru.springframework.api.v1.mapper;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.Test;

import static guru.springframework.TestUtils.ID1;
import static guru.springframework.TestUtils.NAME1;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
class CategoryMapperTest {

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    void categoryToCategoryDTOTest() {
        // Given
        Category category = Category.builder().id(ID1).name(NAME1).build();

        // When
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        // Then
        assertNotNull(categoryDTO);
        assertEquals(ID1, categoryDTO.getId());
        assertEquals(NAME1, categoryDTO.getName());
    }
}
