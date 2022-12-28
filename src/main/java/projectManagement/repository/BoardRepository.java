package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectManagement.entities.board.Board;


public interface BoardRepository extends JpaRepository<Board, Long> {
}
