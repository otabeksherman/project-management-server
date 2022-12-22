package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectManagement.entities.item.Item;
import projectManagement.entities.user.User;

public interface ItemRepository  extends JpaRepository<Item, Long> {

}
