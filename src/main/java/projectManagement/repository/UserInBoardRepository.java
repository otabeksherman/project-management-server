package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectManagement.entities.user.UserInBoard;

import java.util.List;

public interface UserInBoardRepository extends JpaRepository<UserInBoard, Long> {
    @Query("SELECT a FROM Authorization a WHERE a.board.id=?1 AND a.user.id=?2")
    List<UserInBoard> findByBoardAndUser(Long boardId, Long userId);
}