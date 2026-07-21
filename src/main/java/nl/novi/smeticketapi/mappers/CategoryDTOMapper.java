package nl.novi.smeticketapi.mappers;

import nl.novi.smeticketapi.dtos.category.CategoryResponseDTO;
import nl.novi.smeticketapi.dtos.category.CategoryRequestDTO;
import nl.novi.smeticketapi.entities.CategoryEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryDTOMapper implements DTOMapper<CategoryResponseDTO, CategoryRequestDTO, CategoryEntity>{

    @Override
    public CategoryResponseDTO mapToDto(CategoryEntity entity){

        var result = new CategoryResponseDTO();
        result.setId(entity.getId());
        result.setDescription(entity.getDescription());
        result.setName(entity.getName());
        return result;
    }

    @Override
    public List<CategoryResponseDTO> mapToDto(List<CategoryEntity> entities){
        var result = new ArrayList<CategoryResponseDTO>();
        for (CategoryEntity entity : entities){
            result.add(mapToDto(entity));
        }
        return result;
    }

    @Override
    public CategoryEntity mapToEntity(CategoryRequestDTO dto){
        var result = new CategoryEntity();
        result.setName(dto.getName());
        result.setDescription(dto.getDescription());
        return result;
    }
}
