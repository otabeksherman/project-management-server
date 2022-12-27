package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectManagement.entities.item.Item;

import java.util.List;


public interface ItemRepository  extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.parent =?1")
    List<Item> findAllSubItems(Item item);
}
