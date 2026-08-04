package nl.novi.smeticketapi.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "internal_notes")
public class InternalNoteEntity extends BaseEntity{

    @Column(columnDefinition = "TEXT", nullable = false)
    private String noteText;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketEntity ticket;

    @ManyToOne
    @JoinColumn(name = "sme_username")
    private UserEntity sme;

    //Constructor
    public InternalNoteEntity(){}

    //Getters and setters
    public String getNoteText() {return noteText;}
    public void setNoteText(String noteText) {this.noteText = noteText;}

    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public TicketEntity getTicket() {return ticket;}
    public void setTicket(TicketEntity ticket) {this.ticket = ticket;}

    public UserEntity getSme() {return sme;}
    public void setSme(UserEntity sme) {this.sme = sme;}
}
