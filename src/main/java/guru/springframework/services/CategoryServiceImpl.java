package guru.springframework.services;

import guru.springframework.api.v1.mapper.CategoryMapper;
import guru.springframework.api.v1.model.CategoryDTO;
import guru.springframework.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gaetan Bloch
 * Created on 13/04/2020
 */
@RequiredArgsConstructor
@Service
final class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::categoryToCategoryDTO)
                // TODO Handle NotFoundException
                .orElseThrow(RuntimeException::new);
    }
}
