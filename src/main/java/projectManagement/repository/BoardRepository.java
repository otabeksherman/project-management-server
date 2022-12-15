package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectManagement.entities.board.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
