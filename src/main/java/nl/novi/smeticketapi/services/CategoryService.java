package nl.novi.smeticketapi.services;
import jakarta.validation.Valid;
import nl.novi.smeticketapi.dtos.category.CategoryRequestDTO;
import nl.novi.smeticketapi.dtos.category.CategoryResponseDTO;
import nl.novi.smeticketapi.entities.CategoryEntity;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.CategoryDTOMapper;
import nl.novi.smeticketapi.repositories.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    //Constructor
    public CategoryService(CategoryRepository categoryRepository, CategoryDTOMapper categoryDTOMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    //Methods

    //Method to retrieve a list of all categories
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryDTOMapper.mapToDto(categoryRepository.findAll());
    }

    //Private method to retrieve category entity
    private CategoryEntity getCategoryEntity(Long id) {
        CategoryEntity existingCategoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category " + id + " not found"));
        return existingCategoryEntity;
    }

    //Method to retrieve a specific category by id
    public CategoryResponseDTO getCategoryById(Long id) {
        CategoryEntity categoryEntity = getCategoryEntity(id);
        return categoryDTOMapper.mapToDto(categoryEntity);
    }

    //Method to create a new category
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        CategoryEntity categoryEntity = categoryDTOMapper.mapToEntity(requestDTO);
        categoryEntity = categoryRepository.save(categoryEntity);
        return categoryDTOMapper.mapToDto(categoryEntity);
    }

    //Method to edit a category
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {
        CategoryEntity existingCategoryEntity = getCategoryEntity(id);
        existingCategoryEntity.setName(requestDTO.getName());
        existingCategoryEntity.setDescription(requestDTO.getDescription());
        existingCategoryEntity = categoryRepository.save(existingCategoryEntity);
        return categoryDTOMapper.mapToDto(existingCategoryEntity);
    }

    //Method to delete a category
    public void deleteCategory(Long id) {
        CategoryEntity existingCategory = getCategoryEntity(id);
        categoryRepository.delete(existingCategory);
    }
}
