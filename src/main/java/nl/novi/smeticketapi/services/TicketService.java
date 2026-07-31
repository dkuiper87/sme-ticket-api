package nl.novi.smeticketapi.services;

import nl.novi.smeticketapi.mappers.TicketDTOMapper;
import nl.novi.smeticketapi.repositories.CategoryRepository;
import nl.novi.smeticketapi.repositories.CourseRepository;
import nl.novi.smeticketapi.repositories.TicketRepository;
import nl.novi.smeticketapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
    private TicketRepository ticketRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private CourseRepository courseRepository;
    private TicketDTOMapper ticketDTOMapper;

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

}
