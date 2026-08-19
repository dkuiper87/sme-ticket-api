package nl.novi.smeticketapi.services;

import nl.novi.smeticketapi.dtos.ticket.TicketRequestDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketResponseDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketUpdateRequestDTO;
import nl.novi.smeticketapi.entities.*;
import nl.novi.smeticketapi.enums.TicketStatus;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.TicketDTOMapper;
import nl.novi.smeticketapi.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final TagRepository tagRepository;
    private final TicketDTOMapper ticketDTOMapper;

    //Constructor
    public TicketService(
            TicketRepository ticketRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            CourseRepository courseRepository,
            TagRepository tagRepository,
            TicketDTOMapper ticketDTOMapper
    ){
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
        this.tagRepository = tagRepository;
        this.ticketDTOMapper = ticketDTOMapper;
    }

    //Methods

    //Method to retrieve a list of all tickets
    public List<TicketResponseDTO> getAllTickets() {
        return ticketDTOMapper.mapToDto(ticketRepository.findAll());
    }

    //Private method to retrieve ticket entity
    private TicketEntity getTicketEntity(Long id) {
        TicketEntity existingTicketEntity = ticketRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Ticket " + id + " not found"));
        return existingTicketEntity;
    }

    //Private method to retrieve user entity
    private UserEntity getUserEntity(String username) {
        UserEntity userEntity = userRepository.findById(username)
                .orElseThrow(() -> new RecordNotFoundException("User " + username + " not found"));
        return userEntity;
    }

    //Private method to retrieve category entity
    private CategoryEntity getCategoryEntity(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category " + id + " not found"));
        return categoryEntity;
    }

    //Private method to retrieve course entity
    private CourseEntity getCourseEntity(Long id) {
        CourseEntity courseEntity = courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Course " + id + " not found"));
        return courseEntity;
    }

    //Private method to retrieve tag entity
    private TagEntity getTagEntity(Long id) {
        TagEntity tagEntity = tagRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Tag " + id + " not found"));
        return tagEntity;
    }

    //Method to retrieve a specific ticket by id
    public TicketResponseDTO getTicketById(Long id) {
        TicketEntity ticketEntity = getTicketEntity(id);
        return ticketDTOMapper.mapToDto(ticketEntity);
    }

    // Method to create a new ticket
    public TicketResponseDTO createTicket(TicketRequestDTO requestDTO, String studentUsername) {
        TicketEntity ticketEntity = ticketDTOMapper.mapToEntity(requestDTO);
        UserEntity studentEntity = getUserEntity(studentUsername);
        ticketEntity.setStudent(studentEntity);
        CategoryEntity categoryEntity = getCategoryEntity(requestDTO.getCategoryId());
        ticketEntity.setCategory(categoryEntity);
        CourseEntity courseEntity = getCourseEntity(requestDTO.getCourseId());
        ticketEntity.setCourse(courseEntity);
        ticketEntity = ticketRepository.save(ticketEntity);
        return ticketDTOMapper.mapToDto(ticketEntity);
    }

    //Method for sme to claim and/or change the status of a ticket
    public TicketResponseDTO updateTicket(Long id, TicketUpdateRequestDTO requestDTO) {
        TicketEntity existingTicketEntity = getTicketEntity(id);
        if(requestDTO.getStatus() != null) {
            existingTicketEntity.setStatus(requestDTO.getStatus());
        }
        if(requestDTO.getSmeUsername() != null) {
            UserEntity sme = getUserEntity(requestDTO.getSmeUsername());
            if(existingTicketEntity.getStatus() == TicketStatus.OPEN) {
                existingTicketEntity.setStatus(TicketStatus.IN_BEHANDELING);
            }
            existingTicketEntity.setSme(sme);
        }
        existingTicketEntity = ticketRepository.save(existingTicketEntity);
        return ticketDTOMapper.mapToDto(existingTicketEntity);
    }

    //Method for sme to update ticket tags
    public TicketResponseDTO updateTicketTags(Long id, Set<Long> tagIds) {
        TicketEntity existingTicketEntity = getTicketEntity(id);
        Set<TagEntity> tagEntitySet = new HashSet<>();
        for (Long tagId :  tagIds) {
            tagEntitySet.add(getTagEntity(tagId));
        }
        existingTicketEntity.setTags(tagEntitySet);
        existingTicketEntity = ticketRepository.save(existingTicketEntity);
        return ticketDTOMapper.mapToDto(existingTicketEntity);
    }

    //Method to delete a ticket
    public void deleteTicket(Long id) {
        TicketEntity existingTicket = getTicketEntity(id);
        ticketRepository.delete(existingTicket);
    }

    //Filter methods

    //Method to retrieve a list of all tickets by a specific student
    public List<TicketResponseDTO> getAllTicketsByStudentUsername(String username) {
        return ticketDTOMapper.mapToDto(ticketRepository.findByStudent_Username(username));
    }

    //Method to retrieve a list of all tickets claimed by a specific sme
    public List<TicketResponseDTO> getAllTicketsBySmeUsername(String username) {
        return ticketDTOMapper.mapToDto(ticketRepository.findBySme_Username(username));
    }

    //Method to retrieve a list of all tickets with a certain status
    public List<TicketResponseDTO> getAllTicketsByStatus(TicketStatus status) {
        return ticketDTOMapper.mapToDto(ticketRepository.findByStatus(status));
    }
}
