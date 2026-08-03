package nl.novi.smeticketapi.dtos.ticket;

import java.util.Set;

public class TicketTagRequestDTO {
    private Set<Long> tagIds;

    //Getters and setters
    public Set<Long> getTagIds() {return tagIds;}
    public void setTagIds(Set<Long> tagIds) {this.tagIds = tagIds;}
}
