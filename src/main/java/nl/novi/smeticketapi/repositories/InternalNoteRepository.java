package nl.novi.smeticketapi.repositories;

import nl.novi.smeticketapi.entities.InternalNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternalNoteRepository extends JpaRepository<InternalNoteEntity, Long> {
}
