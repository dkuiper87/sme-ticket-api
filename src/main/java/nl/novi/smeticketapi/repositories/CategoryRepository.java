package nl.novi.smeticketapi.repositories;

import nl.novi.smeticketapi.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
