package nl.novi.smeticketapi.controllers;

import jakarta.validation.Valid;
import nl.novi.smeticketapi.dtos.attachment.AttachmentResponseDTO;
import nl.novi.smeticketapi.dtos.internalnote.InternalNoteRequestDTO;
import nl.novi.smeticketapi.dtos.internalnote.InternalNoteResponseDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketRequestDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketResponseDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketTagRequestDTO;
import nl.novi.smeticketapi.dtos.ticket.TicketUpdateRequestDTO;
import nl.novi.smeticketapi.entities.AttachmentEntity;
import nl.novi.smeticketapi.enums.TicketStatus;
import nl.novi.smeticketapi.services.AttachmentService;
import nl.novi.smeticketapi.services.InternalNoteService;
import nl.novi.smeticketapi.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final InternalNoteService internalNoteService;
    private final AttachmentService attachmentService;

    // Constructor
    public TicketController(
            TicketService ticketService,
            InternalNoteService internalNoteService,
            AttachmentService attachmentService
    ) {
        this.ticketService = ticketService;
        this.internalNoteService = internalNoteService;
        this.attachmentService = attachmentService;
    }

    // GET /tickets - Returns a list of tickets based on user role
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> getAllTickets(
            @RequestParam(value = "student", required = false) String studentUsername,
            @RequestParam(value = "sme", required = false) String smeUsername,
            @RequestParam(value = "status", required = false) TicketStatus status,
            Authentication authentication,
            @AuthenticationPrincipal Jwt jwt
    ) {
        String loggedInUser = jwt.getClaimAsString("preferred_username");

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isSme = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_SME"));

        List<TicketResponseDTO> tickets;

        if (isAdmin) {
            if (studentUsername != null) tickets = ticketService.getAllTicketsByStudentUsername(studentUsername);
            else if (smeUsername != null) tickets = ticketService.getAllTicketsBySmeUsername(smeUsername);
            else if (status != null) tickets = ticketService.getAllTicketsByStatus(status);
            else tickets = ticketService.getAllTickets();
        } else if (isSme) {
            tickets = ticketService.getAllTicketsBySmeUsername(loggedInUser);
        } else {
            tickets = ticketService.getAllTicketsByStudentUsername(loggedInUser);
        }

        return ResponseEntity.ok(tickets);
    }

    // GET /tickets/{id} - Returns a ticket by id
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getTicketById(@PathVariable Long id) {
        TicketResponseDTO ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    // POST /tickets - Creates a new ticket
    @PostMapping
    public ResponseEntity<TicketResponseDTO> createTicket(
            @RequestBody @Valid TicketRequestDTO requestDTO,
            @AuthenticationPrincipal Jwt jwt) {

        String username = jwt.getClaimAsString("preferred_username");

        TicketResponseDTO newTicket = ticketService.createTicket(requestDTO, username);

        URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTicket.getId())
                .toUri();

        return ResponseEntity.created(location).body(newTicket);
    }

    // PATCH /tickets/{id} - Claim ticket and/or update ticket status
    @PatchMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> updateTicket(@PathVariable Long id, @RequestBody TicketUpdateRequestDTO requestDTO) {
        TicketResponseDTO updatedTicket = ticketService.updateTicket(id, requestDTO);
        return ResponseEntity.ok(updatedTicket);
    }

    // PUT /tickets/{id}/tags - Update tags assigned to the ticket
    @PutMapping("/{id}/tags")
    public ResponseEntity<TicketResponseDTO> updateTicketTags(@PathVariable Long id, @RequestBody TicketTagRequestDTO requestDTO) {
        TicketResponseDTO updatedTicket = ticketService.updateTicketTags(id, requestDTO.getTagIds());
        return ResponseEntity.ok(updatedTicket);
    }

    // DELETE /tickets/{id} - Delete a ticket
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    // POST /tickets/{id}/notes - Create a new internal note for a ticket
    @PostMapping("/{id}/notes")
    public ResponseEntity<InternalNoteResponseDTO> createInternalNote(
            @PathVariable("id") Long ticketId,
            @Valid @RequestBody InternalNoteRequestDTO requestDTO,
            @AuthenticationPrincipal Jwt jwt) {

        String username = jwt.getClaimAsString("preferred_username");

        InternalNoteResponseDTO createdNote = internalNoteService.createInternalNote(ticketId, requestDTO, username);

        URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{noteId}")
                .buildAndExpand(createdNote.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdNote);
    }

    // DELETE /tickets/notes/{noteId} - Delete an internal note
    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<Void> deleteInternalNote(@PathVariable Long noteId) {
        internalNoteService.deleteInternalNote(noteId);
        return ResponseEntity.noContent().build();
    }

    // POST /tickets/{id}/attachments - Uploads a file and attaches it to the ticket
    @PostMapping("/{id}/attachments")
    public ResponseEntity<AttachmentResponseDTO> uploadAttachment(
            @PathVariable("id") Long ticketId,
            @RequestParam("file") MultipartFile file) {

        AttachmentResponseDTO uploadedAttachment = attachmentService.uploadAttachment(ticketId, file);

        URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{attachmentId}")
                .buildAndExpand(uploadedAttachment.getId())
                .toUri();

        return ResponseEntity.created(location).body(uploadedAttachment);
    }

    // GET /tickets/attachments/{attachmentId} - Download a specific attachment
    @GetMapping("/attachments/{attachmentId}")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long attachmentId) {
        AttachmentEntity attachment = attachmentService.downloadAttachment(attachmentId);
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set(org.springframework.http.HttpHeaders.CONTENT_TYPE, attachment.getContentType());
        headers.set(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileName() + "\"");
        return new ResponseEntity<>(attachment.getBytes(), headers, org.springframework.http.HttpStatus.OK);
    }
}