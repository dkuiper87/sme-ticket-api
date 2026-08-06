package nl.novi.smeticketapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "attachments")
public class AttachmentEntity extends BaseEntity {
    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String contentType;
    @Lob
    @Column(nullable = false)
    private byte[] bytes;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketEntity ticket;

    //Constructor
    public AttachmentEntity() {}

    //Getters and setters

    public String getFileName() {return fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public String getContentType() {return contentType;}

    public void setContentType(String contentType) {this.contentType = contentType;}

    public byte[] getBytes() {return bytes;}
    public void setBytes(byte[] bytes) {this.bytes = bytes;}

    public TicketEntity getTicket() {return ticket;}
    public void setTicket(TicketEntity ticket) {this.ticket = ticket;}
}
