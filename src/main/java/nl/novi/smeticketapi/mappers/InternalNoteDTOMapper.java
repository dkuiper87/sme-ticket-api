package nl.novi.smeticketapi.mappers;

import nl.novi.smeticketapi.dtos.internalnote.InternalNoteRequestDTO;
import nl.novi.smeticketapi.dtos.internalnote.InternalNoteResponseDTO;
import nl.novi.smeticketapi.entities.InternalNoteEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InternalNoteDTOMapper implements DTOMapper<InternalNoteResponseDTO, InternalNoteRequestDTO, InternalNoteEntity>{
    private final UserDTOMapper userDTOMapper;

    public InternalNoteDTOMapper(UserDTOMapper userDTOMapper){
        this.userDTOMapper = userDTOMapper;
    }

    @Override
    public InternalNoteResponseDTO mapToDto(InternalNoteEntity entity){
        var result = new InternalNoteResponseDTO();
        result.setId(entity.getId());
        result.setNoteText(entity.getNoteText());
        result.setCreatedAt(entity.getCreatedAt());
        result.setSme(userDTOMapper.mapToDto(entity.getSme()));
        return result;
    }

    @Override
    public List<InternalNoteResponseDTO> mapToDto(List<InternalNoteEntity> entities) {
        var result = new ArrayList<InternalNoteResponseDTO>();
        for (InternalNoteEntity entity : entities) {
            result.add(mapToDto(entity));
        }
        return result;
    }

    @Override
    public InternalNoteEntity mapToEntity(InternalNoteRequestDTO dto) {
        var result = new InternalNoteEntity();
        result.setNoteText(dto.getNoteText());
        return result;
    }
}
