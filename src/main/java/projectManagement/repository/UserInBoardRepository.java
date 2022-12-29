package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectManagement.entities.board.Board;
import projectManagement.entities.user.User;
import projectManagement.entities.user.UserInBoard;

import java.util.List;

public interface UserInBoardRepository extends JpaRepository<UserInBoard, Long> {
    @Query("SELECT a FROM UserInBoard a WHERE a.board.id=?1 AND a.user.email=?2")
    UserInBoard findByBoardAndUser(Long boardId, String userEmail);

    List<UserInBoard> findByBoard_Id(Long id);

    List<UserInBoard> findByUser_Email(String email);

}
