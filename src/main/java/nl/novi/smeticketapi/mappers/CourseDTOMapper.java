package nl.novi.smeticketapi.mappers;

import nl.novi.smeticketapi.dtos.course.CourseRequestDTO;
import nl.novi.smeticketapi.dtos.course.CourseResponseDTO;
import nl.novi.smeticketapi.entities.CourseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseDTOMapper implements DTOMapper<CourseResponseDTO, CourseRequestDTO, CourseEntity>{

    @Override
    public CourseResponseDTO mapToDto(CourseEntity entity){
        var result = new CourseResponseDTO();
        result.setId(entity.getId());
        result.setDescription(entity.getDescription());
        result.setName(entity.getName());
        return result;
    }

    @Override
    public List<CourseResponseDTO> mapToDto(List<CourseEntity> entities){
        var result = new ArrayList<CourseResponseDTO>();
        for (CourseEntity entity : entities){
            result.add(mapToDto(entity));
        }
        return result;
    }

    @Override
    public CourseEntity mapToEntity(CourseRequestDTO dto){
        var result = new CourseEntity();
        result.setName(dto.getName());
        result.setDescription(dto.getDescription());
        return result;
    }
}