package nl.novi.smeticketapi.services;
import nl.novi.smeticketapi.dtos.category.CategoryResponseDTO;
import nl.novi.smeticketapi.mappers.CategoryDTOMapper;
import nl.novi.smeticketapi.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

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
    public List<CategoryResponseDTO> getAllCategories(){
        return categoryDTOMapper.mapToDto(categoryRepository.findAll());
    }
}
