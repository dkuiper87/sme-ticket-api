package nl.novi.smeticketapi.repositories;

import nl.novi.smeticketapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    //Check if username already exists
    boolean existsById(String username);
}
