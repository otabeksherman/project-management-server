package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectManagement.entities.board.Board;
import projectManagement.entities.user.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    @Query("SELECT u.boards from User u where u.email = ?1")
    List<Board> findBoards(String email);
}
