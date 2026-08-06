package nl.novi.smeticketapi.repositories;

import nl.novi.smeticketapi.entities.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<AttachmentEntity, Long> {
}
