package nl.novi.smeticketapi.mappers;

import nl.novi.smeticketapi.dtos.attachment.AttachmentResponseDTO;
import nl.novi.smeticketapi.entities.AttachmentEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AttachmentDTOMapper {

    public AttachmentResponseDTO mapToDto(AttachmentEntity entity) {
        var result = new AttachmentResponseDTO();
        result.setId(entity.getId());
        result.setFileName(entity.getFileName());
        result.setContentType(entity.getContentType());
        return result;
    }

    public List<AttachmentResponseDTO> mapToDto(List<AttachmentEntity> entities) {
        var result = new ArrayList<AttachmentResponseDTO>();
        for (AttachmentEntity entity : entities) {
            result.add(mapToDto(entity));
        }
        return result;
    }
}