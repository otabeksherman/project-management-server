package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectManagement.entities.item.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
