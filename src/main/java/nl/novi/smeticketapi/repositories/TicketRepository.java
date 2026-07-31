package nl.novi.smeticketapi.repositories;

import nl.novi.smeticketapi.entities.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository  extends JpaRepository<TicketEntity, Long> {
}
