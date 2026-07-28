package nl.novi.smeticketapi.repositories;

import nl.novi.smeticketapi.entities.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
}
