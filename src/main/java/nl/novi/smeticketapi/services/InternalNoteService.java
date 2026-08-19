package nl.novi.smeticketapi.services;

import nl.novi.smeticketapi.dtos.internalnote.InternalNoteRequestDTO;
import nl.novi.smeticketapi.dtos.internalnote.InternalNoteResponseDTO;
import nl.novi.smeticketapi.entities.InternalNoteEntity;
import nl.novi.smeticketapi.entities.TicketEntity;
import nl.novi.smeticketapi.entities.UserEntity;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.InternalNoteDTOMapper;
import nl.novi.smeticketapi.repositories.InternalNoteRepository;
import nl.novi.smeticketapi.repositories.TicketRepository;
import nl.novi.smeticketapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class InternalNoteService {
    private final InternalNoteRepository internalNoteRepository;
    private final InternalNoteDTOMapper internalNoteDTOMapper;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    //Constructor
    public InternalNoteService(
            InternalNoteRepository internalNoteRepository,
            InternalNoteDTOMapper internalNoteDTOMapper,
            TicketRepository ticketRepository,
            UserRepository userRepository
    ){
        this.internalNoteRepository = internalNoteRepository;
        this.internalNoteDTOMapper = internalNoteDTOMapper;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    //Methods
    //Private method to retrieve internal note entity
    private InternalNoteEntity getInternalNoteEntity(Long id) {
        InternalNoteEntity internalNoteEntity = internalNoteRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Note " + id + " not found"));
        return internalNoteEntity;
    }

    //Private method to retrieve ticket entity
    private TicketEntity getTicketEntity(Long id) {
        TicketEntity ticketEntity = ticketRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Ticket " + id + " not found"));
        return ticketEntity;
    }

    //Private method to retrieve user entity
    private UserEntity getUserEntity(String username) {
        UserEntity userEntity = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User " + username + " not found"));
        return userEntity;
    }

    //Method to retrieve a specific note by id
    public InternalNoteResponseDTO getInternalNoteById(Long id){
        InternalNoteEntity internalNoteEntity = getInternalNoteEntity(id);
        return internalNoteDTOMapper.mapToDto(internalNoteEntity);
    }

    //Method to create a new internal note
    public InternalNoteResponseDTO createInternalNote(Long ticketId, InternalNoteRequestDTO requestDTO, String smeUsername) {
        InternalNoteEntity internalNoteEntity = internalNoteDTOMapper.mapToEntity(requestDTO);
        TicketEntity ticketEntity = getTicketEntity(ticketId);
        internalNoteEntity.setTicket(ticketEntity);
        UserEntity smeEntity = getUserEntity(smeUsername);
        internalNoteEntity.setSme(smeEntity);
        internalNoteEntity = internalNoteRepository.save(internalNoteEntity);
        return internalNoteDTOMapper.mapToDto(internalNoteEntity);
    }

    //Method to delete an internal note
    public void deleteInternalNote(Long id) {
        InternalNoteEntity existingInternalNote = getInternalNoteEntity(id);
        internalNoteRepository.delete(existingInternalNote);
    }
}