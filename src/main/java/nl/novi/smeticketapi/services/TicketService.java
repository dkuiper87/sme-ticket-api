package nl.novi.smeticketapi.services;

import nl.novi.smeticketapi.dtos.ticket.TicketRequestDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketResponseDTO;
import nl.novi.smeticketapi.entities.CategoryEntity;
import nl.novi.smeticketapi.entities.CourseEntity;
import nl.novi.smeticketapi.entities.TicketEntity;
import nl.novi.smeticketapi.entities.UserEntity;
import nl.novi.smeticketapi.exceptions.RecordNotFoundException;
import nl.novi.smeticketapi.mappers.TicketDTOMapper;
import nl.novi.smeticketapi.repositories.CategoryRepository;
import nl.novi.smeticketapi.repositories.CourseRepository;
import nl.novi.smeticketapi.repositories.TicketRepository;
import nl.novi.smeticketapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final TicketDTOMapper ticketDTOMapper;

    //Constructor
    public TicketService(
            TicketRepository ticketRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            CourseRepository courseRepository,
            TicketDTOMapper ticketDTOMapper
    ){
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
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

    //Method to retrieve a specific ticket by id
    public TicketResponseDTO getTicketById(Long id) {
        TicketEntity ticketEntity = getTicketEntity(id);
        return ticketDTOMapper.mapToDto(ticketEntity);
    }

    //Method to create a new ticket
    public TicketResponseDTO createTicket(TicketRequestDTO requestDTO) {
        TicketEntity ticketEntity = ticketDTOMapper.mapToEntity(requestDTO);
        UserEntity studentEntity = getUserEntity(requestDTO.getStudentUsername());
        ticketEntity.setStudent(studentEntity);
        CategoryEntity categoryEntity = getCategoryEntity(requestDTO.getCategoryId());
        ticketEntity.setCategory(categoryEntity);
        CourseEntity courseEntity = getCourseEntity(requestDTO.getCourseId());
        ticketEntity.setCourse(courseEntity);
        ticketEntity = ticketRepository.save(ticketEntity);
        return ticketDTOMapper.mapToDto(ticketEntity);
    }

    //Method for sme to claim a ticket

    //Method for sme to change the status of a ticket

    //Method for sme to add tags to a ticket

    //Method to update a ticket

    //Method to delete a ticket
}
