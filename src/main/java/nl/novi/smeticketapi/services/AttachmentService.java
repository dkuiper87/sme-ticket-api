package nl.novi.smeticketapi.services;

import nl.novi.smeticketapi.dtos.attachment.AttachmentResponseDTO;
import nl.novi.smeticketapi.entities.AttachmentEntity;
import nl.novi.smeticketapi.entities.TicketEntity;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.AttachmentDTOMapper;
import nl.novi.smeticketapi.repositories.AttachmentRepository;
import nl.novi.smeticketapi.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final TicketRepository ticketRepository;
    private final AttachmentDTOMapper attachmentDTOMapper;

    //Constructor
    public AttachmentService(
            AttachmentRepository attachmentRepository,
            TicketRepository ticketRepository,
            AttachmentDTOMapper attachmentDTOMapper
    ) {
        this.attachmentRepository = attachmentRepository;
        this.ticketRepository = ticketRepository;
        this.attachmentDTOMapper = attachmentDTOMapper;
    }

    //Methods

    //Private method to retrieve ticket entity
    private TicketEntity getTicketEntity(Long id) {
        TicketEntity ticketEntity = ticketRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Ticket " + id + " not found"));
        return ticketEntity;
    }

    //Method to upload attachment
    public AttachmentResponseDTO uploadAttachment(Long ticketId, MultipartFile file){
        TicketEntity ticketEntity = getTicketEntity(ticketId);
        AttachmentEntity attachment = new AttachmentEntity();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setContentType(file.getContentType());
        try {
            attachment.setBytes(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Kon het bestand niet opslaan", e);
        }
        attachment.setTicket(ticketEntity);
        attachment = attachmentRepository.save(attachment);
        return attachmentDTOMapper.mapToDto(attachment);
    }

    // Method to download a specific attachment
    public AttachmentEntity downloadAttachment(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Attachment " + id + " not found"));
    }
}
