package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.ItemType;

import java.util.List;
import java.util.Set;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
