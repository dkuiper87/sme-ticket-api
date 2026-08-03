package nl.novi.smeticketapi.dtos.internalnote;

import nl.novi.smeticketapi.dtos.ticket.TicketResponseDTO;
import nl.novi.smeticketapi.dtos.user.UserResponseDTO;

import java.time.LocalDateTime;

public class InternalNoteResponseDTO {
    private Long id;
    private String noteText;
    private LocalDateTime createdAt;
    private TicketResponseDTO ticket;
    private UserResponseDTO sme;

    //Getters and setters
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNoteText() {return noteText;}
    public void setNoteText(String noteText) {this.noteText = noteText;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public TicketResponseDTO getTicket() {return ticket;}
    public void setTicket(TicketResponseDTO ticket) {this.ticket = ticket;}

    public UserResponseDTO getSme() {return sme;}
    public void setSme(UserResponseDTO sme) {this.sme = sme;}
}
