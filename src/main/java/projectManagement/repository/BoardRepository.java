package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.ItemType;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<ItemType> findAllById(Long id);
}

