package nl.novi.smeticketapi.repositories;

import nl.novi.smeticketapi.entities.TicketEntity;
import nl.novi.smeticketapi.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository  extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> findByStudent_Username(String username);
    List<TicketEntity> findBySme_Username(String username);
    List<TicketEntity> findByStatus(TicketStatus status);
}
