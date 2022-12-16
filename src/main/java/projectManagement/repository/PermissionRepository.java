package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projectManagement.entities.user.Authorization;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Authorization, Integer> {

    @Query("SELECT a FROM Authorization a WHERE a.document.id=?1 AND a.user.id=?2")
    List<Authorization> findByBoardAndUser(Long boardId, Long userId);

    @Query("SELECT a FROM Authorization a WHERE a.user.id=?1")
    List<Authorization> findByUser(Long userId);

    @Transactional
    @Modifying
    @Query("DELETE Authorization a WHERE a.document.id=?1")
    void deleteByBoardId(Long boardId);
}