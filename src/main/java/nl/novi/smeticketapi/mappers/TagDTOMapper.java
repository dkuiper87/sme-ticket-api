package nl.novi.smeticketapi.mappers;

import nl.novi.smeticketapi.dtos.tag.TagRequestDTO;
import nl.novi.smeticketapi.dtos.tag.TagResponseDTO;
import nl.novi.smeticketapi.entities.TagEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagDTOMapper implements DTOMapper<TagResponseDTO, TagRequestDTO, TagEntity>{
    @Override
    public TagResponseDTO mapToDto(TagEntity entity){
        var result = new TagResponseDTO();
        result.setId(entity.getId());
        result.setName(entity.getName());
        result.setColorHex(entity.getColorHex());
        return result;
    }

    @Override
    public List<TagResponseDTO> mapToDto(List<TagEntity> entities){
        var result = new ArrayList<TagResponseDTO>();
        for (TagEntity entity : entities){
            result.add(mapToDto(entity));
        }
        return result;
    }

    @Override
    public TagEntity mapToEntity(TagRequestDTO dto){
        var result = new TagEntity();
        result.setName(dto.getName());
        result.setColorHex(dto.getColorHex());
        return result;
    }
}
