package nl.novi.smeticketapi.services;

import nl.novi.smeticketapi.dtos.category.CategoryRequestDTO;
import nl.novi.smeticketapi.dtos.category.CategoryResponseDTO;
import nl.novi.smeticketapi.entities.CategoryEntity;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.CategoryDTOMapper;
import nl.novi.smeticketapi.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryDTOMapper categoryDTOMapper;

    @InjectMocks
    private CategoryService categoryService;


    //TESTS

    //Test getAllCategories
    @Test
    void getAllCategories_ShouldReturnListOfAllCategories(){
        //ARRANGE
        //Create fake entity
        CategoryEntity fakeEntity = new CategoryEntity();
        fakeEntity.setId(1L);
        fakeEntity.setName("Java");

        //Wrap in list
        List<CategoryEntity> fakeEntityList = List.of(fakeEntity);

        //Create fake DTO
        CategoryResponseDTO fakeDto = new CategoryResponseDTO();
        fakeDto.setId(1L);
        fakeDto.setName("Java");

        //Wrap in list
        List<CategoryResponseDTO> fakeDtoList = List.of(fakeDto);

        //Program Mocks
        when(categoryRepository.findAll()).thenReturn(fakeEntityList);
        when(categoryDTOMapper.mapToDto(fakeEntityList)).thenReturn(fakeDtoList);

        //ACT
        List<CategoryResponseDTO> result = categoryService.getAllCategories();

        //ASSERT
        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getName());
    }

    //Test getCategoryById - Category is found
    @Test
    void getCategoryById_ShouldReturnCategory(){
        //ARRANGE
        Long categoryId = 1L;

        //Create fake entity
        CategoryEntity fakeEntity = new CategoryEntity();
        fakeEntity.setId(categoryId);
        fakeEntity.setName("Java");

        //Create fake DTO
        CategoryResponseDTO fakeDto = new CategoryResponseDTO();
        fakeDto.setId(categoryId);
        fakeDto.setName("Java");

        //Program Mocks
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(fakeEntity));
        when(categoryDTOMapper.mapToDto(fakeEntity)).thenReturn(fakeDto);

        //ACT
        CategoryResponseDTO result = categoryService.getCategoryById(categoryId);

        //ASSERT
        assertEquals("Java", result.getName());
        assertEquals(1L, result.getId());
    }

    //Test getCategoryById - Category is not found
    @Test
    void getCategoryById_ShouldThrowException_WhenCategoryNotFound() {
        //ARRANGE
        Long categoryId = 999L;

        //Return empty optional
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        //ACT & ASSERT
        org.junit.jupiter.api.Assertions.assertThrows(RecordNotFoundException.class, () -> {
            categoryService.getCategoryById(categoryId);
        });
    }

    //Test createCategory
    @Test
    void createCategory_ShouldReturnCreatedCategory() {
        //ARRANGE
        //Create fake request DTO
        CategoryRequestDTO requestDto = new CategoryRequestDTO();
        requestDto.setName("Spring Boot");

        //Create fake entity to save (no id yet)
        CategoryEntity entityToSave = new CategoryEntity();
        entityToSave.setName("Spring Boot");

        //Create fake response entity (with id)
        CategoryEntity savedEntity = new CategoryEntity();
        savedEntity.setId(2L);
        savedEntity.setName("Spring Boot");

        //Create fake response DTO
        CategoryResponseDTO responseDto = new CategoryResponseDTO();
        responseDto.setId(2L);
        responseDto.setName("Spring Boot");

        //Program Mocks
        when(categoryDTOMapper.mapToEntity(requestDto)).thenReturn(entityToSave);
        when(categoryRepository.save(entityToSave)).thenReturn(savedEntity);
        when(categoryDTOMapper.mapToDto(savedEntity)).thenReturn(responseDto);

        //ACT
        CategoryResponseDTO result = categoryService.createCategory(requestDto);

        //ASSERT
        assertEquals("Spring Boot", result.getName());
        assertEquals(2L, result.getId());
    }

    //Test updateCategory
    @Test
    void updateCategory_ShouldReturnUpdatedCategory() {
        //ARRANGE
        Long categoryId = 1L;

        //Create fake request DTO
        CategoryRequestDTO requestDto = new CategoryRequestDTO();
        requestDto.setName("Updated Java");
        requestDto.setDescription("Updated description");

        //Create fake existing entity
        CategoryEntity existingEntity = new CategoryEntity();
        existingEntity.setId(categoryId);
        existingEntity.setName("Old Java");
        existingEntity.setDescription("Old description");

        //Create fake saved entity
        CategoryEntity savedEntity = new CategoryEntity();
        savedEntity.setId(categoryId);
        savedEntity.setName("Updated Java");
        savedEntity.setDescription("Updated description");

        //Create fake response DTO
        CategoryResponseDTO responseDto = new CategoryResponseDTO();
        responseDto.setId(categoryId);
        responseDto.setName("Updated Java");
        responseDto.setDescription("Updated description");

        //Program Mocks
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingEntity));
        when(categoryRepository.save(existingEntity)).thenReturn(savedEntity);
        when(categoryDTOMapper.mapToDto(savedEntity)).thenReturn(responseDto);

        //ACT
        CategoryResponseDTO result = categoryService.updateCategory(categoryId, requestDto);

        //ASSERT
        assertEquals("Updated Java", result.getName());
        assertEquals("Updated description", result.getDescription());
        assertEquals(categoryId, result.getId());
    }

    //Test deleteCategory
    @Test
    void deleteCategory_ShouldDeleteCategory() {
        //ARRANGE
        Long categoryId = 1L;

        //Create fake existing entity
        CategoryEntity existingEntity = new CategoryEntity();
        existingEntity.setId(categoryId);

        //Program Mocks
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingEntity));

        //ACT
        categoryService.deleteCategory(categoryId);

        //ASSERT
        //Verify that the delete method on the repository was called exactly once with our entity
        org.mockito.Mockito.verify(categoryRepository, org.mockito.Mockito.times(1)).delete(existingEntity);
    }


}
