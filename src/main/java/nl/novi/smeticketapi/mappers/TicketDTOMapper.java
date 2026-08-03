package nl.novi.smeticketapi.mappers;

import nl.novi.smeticketapi.dtos.tag.TagResponseDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketRequestDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketResponseDTO;
import nl.novi.smeticketapi.entities.TagEntity;
import nl.novi.smeticketapi.entities.TicketEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class TicketDTOMapper implements DTOMapper<TicketResponseDTO, TicketRequestDTO, TicketEntity>{
    private final UserDTOMapper userDTOMapper;
    private final CategoryDTOMapper categoryDTOMapper;
    private final CourseDTOMapper courseDTOMapper;
    private final TagDTOMapper tagDTOMapper;

    public TicketDTOMapper(
            UserDTOMapper userDTOMapper,
            CategoryDTOMapper categoryDTOMapper,
            CourseDTOMapper courseDTOMapper,
            TagDTOMapper tagDTOMapper
            ){
                this.userDTOMapper = userDTOMapper;
                this.categoryDTOMapper = categoryDTOMapper;
                this.courseDTOMapper = courseDTOMapper;
                this.tagDTOMapper = tagDTOMapper;
    }

    @Override
    public TicketResponseDTO mapToDto(TicketEntity entity){
        var result = new TicketResponseDTO();
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        result.setDescription(entity.getDescription());
        result.setStatus(entity.getStatus());
        result.setCreatedAt(entity.getCreatedAt());
        result.setStudent(userDTOMapper.mapToDto(entity.getStudent()));
        if(entity.getSme() != null){
            result.setSme(userDTOMapper.mapToDto(entity.getSme()));
        }
        result.setCategory(categoryDTOMapper.mapToDto(entity.getCategory()));
        result.setCourse(courseDTOMapper.mapToDto(entity.getCourse()));
        if (entity.getTags() != null) {
            Set<TagResponseDTO> tagDTOs = new HashSet<>();
            for (TagEntity tagEntity : entity.getTags()) {
                tagDTOs.add(tagDTOMapper.mapToDto(tagEntity));
            }
            result.setTags(tagDTOs);
        }
        return result;
    }

    @Override
    public List<TicketResponseDTO> mapToDto(List<TicketEntity> entities) {
        var result = new ArrayList<TicketResponseDTO>();
        for (TicketEntity entity : entities) {
            result.add(mapToDto(entity));
        }
        return result;
    }
    @Override
    public TicketEntity mapToEntity(TicketRequestDTO dto) {
        var result = new TicketEntity();
        result.setTitle(dto.getTitle());
        result.setDescription(dto.getDescription());
        return result;
    }
}
