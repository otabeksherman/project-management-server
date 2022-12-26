package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectManagement.entities.item.Item;


public interface ItemRepository  extends JpaRepository<Item, Long> {

}
