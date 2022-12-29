package projectManagment.entites.item;
import org.junit.jupiter.api.Test;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.Item;
import projectManagement.entities.user.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class itemTest {

    @Test
    void testGetType() {
        // Create a mock object with a specific type
        Item item = mock(Item.class);
        when(item.getType()).thenReturn("Task");

        // Call the getType() method on the mock object
        String type = item.getType();

        // Check if the returned type is equal to the expected value
        assertEquals("Task", type);
    }

    @Test
    void testGetStatus() {
        // Create a mock object with a specific status
        Item item = mock(Item.class);
        when(item.getStatus()).thenReturn("In Progress");

        // Call the getStatus() method on the mock object
        String status = item.getStatus();

        // Check if the returned status is equal to the expected value
        assertEquals("In Progress", status);
    }

    @Test
    void testGetParent() {
        // Create a mock object with a specific parent
        Item parent = mock(Item.class);
        Item item = mock(Item.class);
        when(item.getParent()).thenReturn(parent);

        // Call the getParent() method on the mock object
        Item returnedParent = item.getParent();

        // Check if the returned parent is equal to the expected value
        assertEquals(parent, returnedParent);
    }

    @Test
    void testGetBoard() {
        // Create a mock object with a specific board
        Board board = mock(Board.class);
        Item item = mock(Item.class);
        when(item.getBoard()).thenReturn(board);

        // Call the getBoard() method on the mock object
        Board returnedBoard = item.getBoard();

        // Check if the returned board is equal to the expected value
        assertEquals(board, returnedBoard);
    }

    @Test
    void testGetCreator() {
        // Create a mock object with a specific creator
        User creator = mock(User.class);
        Item item = mock(Item.class);
        when(item.getCreator()).thenReturn(creator);

        // Call the getCreator() method on the mock object
        User returnedCreator = item.getCreator();

        // Check if the returned creator is equal to the expected value
        assertEquals(creator, returnedCreator);
    }
}
