package guru.springframework.controllers.v1;

import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springframework.TestUtils.*;
import static guru.springframework.controllers.v1.CategoryController.URL_CATEGORIES;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private CategoryController categoryController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    void ListCategoriesTest() throws Exception {
        // Given
        CategoryDTO category1 = CategoryDTO.builder().id(ID1).name(NAME1).build();
        CategoryDTO category2 = CategoryDTO.builder().id(ID2).name(NAME2).build();
        List<CategoryDTO> categories = Arrays.asList(category1, category2);
        when(categoryService.getAllCategories()).thenReturn(categories);

        // When
        mockMvc.perform(get(URL_CATEGORIES).contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));
    }

    @Test
    void getCategoryByNameTest() throws Exception {
        // Given
        CategoryDTO category = CategoryDTO.builder().id(ID1).name(NAME1).build();
        when(categoryService.getCategoryByName(NAME1)).thenReturn(category);

        // When
        mockMvc.perform(get(URL_CATEGORIES + "/" + NAME1).contentType(MediaType.APPLICATION_JSON))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME1)));
    }
}