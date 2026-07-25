package nl.novi.smeticketapi.repositories;

import nl.novi.smeticketapi.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Long>{
}
