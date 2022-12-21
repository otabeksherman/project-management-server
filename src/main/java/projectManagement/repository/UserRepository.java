package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectManagement.entities.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
