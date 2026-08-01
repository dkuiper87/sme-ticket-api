package nl.novi.smeticketapi.controllers;

import jakarta.validation.Valid;
import nl.novi.smeticketapi.dtos.ticket.TicketRequestDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketResponseDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketUpdateRequestDTO;
import nl.novi.smeticketapi.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    //Constructor
    public TicketController(TicketService ticketService) {this.ticketService = ticketService;}

    //Endpoints
    //GET /tickets - Returns a list of all tickets
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAllTickets() {
        List<TicketResponseDTO> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    //GET /tickets/{id} - Returns a ticket by id
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long id) {
        TicketResponseDTO ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }


    //POST /tickets - Creates a new ticket
    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(@RequestBody @Valid TicketRequestDTO requestDTO) {
        TicketResponseDTO newTicket = ticketService.createTicket(requestDTO);

        URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTicket.getId())
                .toUri();

        return ResponseEntity.created(location).body(newTicket);
    }

    //PATCH /tickets/{"/id"} - Claim ticket and/or update ticket status
    @PatchMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@PathVariable Long id, @RequestBody TicketUpdateRequestDTO requestDTO) {
        TicketResponseDTO updatedTicket = ticketService.updateTicket(id, requestDTO);
        return ResponseEntity.ok(updatedTicket);
    }
}
