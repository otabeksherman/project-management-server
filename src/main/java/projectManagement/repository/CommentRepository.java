package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectManagement.entities.item.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}