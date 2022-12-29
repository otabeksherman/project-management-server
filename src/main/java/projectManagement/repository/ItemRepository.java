package projectManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.Item;

import java.util.Date;
import java.util.List;


public interface ItemRepository  extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.parent =?1")
    List<Item> findAllSubItems(Item item);

    @Query("select i from Item i where i.board = ?1")
    List<Item> findByBoard(Board board);

    @Query("select i from Item i where i.board = ?1 and i.assignedTo.email = ?2")
    List<Item> findByBoardAndAssignedTo_Email(Board board, String email);

    @Query("select i from Item i where i.board = ?1 and i.creator.email = ?2")
    List<Item> findByBoardAndCreator_Email(Board board, String email);

    @Query("select i from Item i where i.board = ?1 and i.dueDate < ?2")
    List<Item> findByBoardAndDueDateBefore(Board board, Date dueDate);

    @Query("select i from Item i where i.board = ?1 and i.dueDate is null")
    List<Item> findByBoardAndDueDateNull(Board board);

    @Query("select i from Item i where i.board = ?1 and i.importance = ?2")
    List<Item> findByBoardAndImportance(Board board, int importance);








}
