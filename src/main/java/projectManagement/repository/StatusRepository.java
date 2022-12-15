package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectManagement.entities.Status;
import projectManagement.entities.item.Item;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
}
