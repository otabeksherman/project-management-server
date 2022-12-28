package projectManagment.controller.entites.board;

import projectManagement.entities.board.Board;
import projectManagement.entities.item.Item;
import projectManagement.entities.user.User;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BoardTest {
    // Create a mock user object
    User user = mock(User.class);


    @Test
    void testGetTypes() {
        // Create a Board object with the mock user
        Board board = new Board();
        board.setUser(user);

        // Set the types of the board
        Set<String> types = new HashSet<>();
        types.add("Task");
        types.add("Bug");
        board.setTypes(types);

        // Verify that the getTypes() method returns the expected types
        assertEquals(types, board.getTypes());
    }

    @Test
    void testSetUser() {
        // Create a Board object and set the user
        Board board = new Board();
        board.setUser(user);

        // Verify that the Board object has the expected user
        assertEquals(user, board.getUser());
    }

    @Test
    void testGetStatuses() {
        // Create a Board object
        Board board = new Board();

        // Set the statuses of the board
        Set<String> statuses = new HashSet<>();
        statuses.add("To Do");
        statuses.add("Doing");
        statuses.add("Done");
        board.setStatuses(statuses);

        // Verify that the getStatuses() method returns the expected statuses
        assertEquals(statuses, board.getStatuses());
    }

}
