package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectManagement.entities.item.ItemType;

public interface TypeRepository extends JpaRepository<ItemType, Long> {
}
