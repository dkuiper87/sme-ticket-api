package nl.novi.smeticketapi.dtos.ticket;

import nl.novi.smeticketapi.enums.TicketStatus;

public class TicketUpdateRequestDTO {
    private String smeUsername;
    private TicketStatus status;

    //Getters and setters
    public String getSmeUsername() {return smeUsername;}
    public void setSmeUsername(String smeUsername) {this.smeUsername = smeUsername;}
    public TicketStatus getStatus() {return status;}
    public void setStatus(TicketStatus status) {this.status = status;}
}
