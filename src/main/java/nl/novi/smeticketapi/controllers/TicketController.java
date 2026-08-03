package nl.novi.smeticketapi.controllers;

import jakarta.validation.Valid;
import nl.novi.smeticketapi.dtos.ticket.TicketRequestDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketResponseDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketTagRequestDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketUpdateRequestDTO;
import nl.novi.smeticketapi.enums.TicketStatus;
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
    //GET /tickets - Returns a list of tickets
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAllTickets(
            @RequestParam(value = "student", required = false) String studentUsername,
            @RequestParam(value = "sme", required = false) String smeUsername,
            @RequestParam(value = "status", required = false) TicketStatus status
    ) {
        List<TicketResponseDTO> tickets;

        if (studentUsername != null) {
            tickets = ticketService.getAllTicketsByStudentUsername(studentUsername);
        } else if (smeUsername != null) {
            tickets = ticketService.getAllTicketsBySmeUsername(smeUsername);
        } else if (status != null) {
            tickets = ticketService.getAllTicketsByStatus(status);
        } else {
            tickets = ticketService.getAllTickets();
        }

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

    //PUT /tickets/{/id}/tags - Update tags assigned to the ticket
    @PutMapping("/{id}/tags")
    public ResponseEntity<TicketResponseDTO> updateTicketTags(@PathVariable Long id, @RequestBody TicketTagRequestDTO requestDTO) {
        TicketResponseDTO updatedTicket = ticketService.updateTicketTags(id, requestDTO.getTagIds());
        return ResponseEntity.ok(updatedTicket);
    }

    //DELETE /tickets/{id} - Delete a ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

}
