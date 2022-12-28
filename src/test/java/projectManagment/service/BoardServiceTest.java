package projectManagment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projectManagement.entities.board.Board;
import projectManagement.entities.user.User;
import projectManagement.repository.BoardRepository;
import projectManagement.repository.UserInBoardRepository;
import projectManagement.repository.UserRepository;
import projectManagement.service.BoardService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserInBoardRepository userInBoardRepository;

    @InjectMocks
    private BoardService boardService;

    private User user;
    private Board board1;
    private Board board2;

    @BeforeEach
    public void setUp() {
        // create mock user and boards
        user = new User("user@example.com", "Bb123465!");
        board1 = new Board("Board 1", user);
        board2 = new Board("Board 2", user);
    }

    @Test
    public void testGetAllBoards() {
        // Arrange
        String email = "user@example.com";
        User user = new User(email, "password");
        Board board1 = new Board("Board 1", user);
        Board board2 = new Board("Board 2", user);
        List<Board> expectedBoards = List.of(board1, board2);
        when(userRepository.findBoards(email)).thenReturn(expectedBoards);

        // Act
        List<Board> actualBoards = boardService.getAllBoards(email);

        // Assert
        assertEquals(expectedBoards, actualBoards);
        verify(userRepository).findBoards(email);
    }


}