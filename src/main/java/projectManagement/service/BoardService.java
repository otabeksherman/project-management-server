package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.dto.ShareBoardDto;
import projectManagement.dto.StatusDto;
import projectManagement.dto.AddTypeDto;
import projectManagement.entities.board.Board;
import projectManagement.entities.item.Item;
import projectManagement.entities.user.User;
import projectManagement.entities.user.UserInBoard;
import projectManagement.entities.user.UserRole;
import projectManagement.repository.BoardRepository;
import projectManagement.repository.ItemRepository;
import projectManagement.repository.UserInBoardRepository;
import projectManagement.repository.UserRepository;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private static Logger logger = LogManager.getLogger(BoardService.class);
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final UserInBoardRepository userInBoardRepository;

    /**
     * This method retrieves all boards that the user with the given email has access to.
     *
     * @param email The email of the user
     * @return A list of boards that the user has access to
     */
    public List<Board> getAllBoards(String email) {
        List<UserInBoard> userInBoards =userInBoardRepository.findByUser_Email(email);
        return userInBoards.stream().map(userInBoard -> userInBoard.getBoard()).collect(Collectors.toList());
    }

/**
 * This method retrieves the board with the given ID, if it exists and the user with the given email has access to it.
 *
 * @param email The email of the user
 * @param id The ID of the board
 * @return The board with the given ID
 */
    public Board getBoard(String email, Long id) throws SQLDataException {
        return boardRepository.findById(id).orElseThrow(SQLDataException::new);
    }
    /**
     * This method creates a new board with the given name and assigns the user with the given email as the owner.
     *
     * @param name The name of the new board
     * @param userEmail The email of the user who will be the owner of the new board
     * @return The newly created board
     */
    public Board create(String name, String userEmail) {
        User user = userRepository.findUserByEmail(userEmail);
        Board board = new Board(name, user);
        UserInBoard userInBoard = new UserInBoard(board, user, UserRole.ADMIN);
        return userInBoardRepository.save(userInBoard).getBoard();
    }

    /**
     * This method adds a new status to the board with the given ID.
     *
     * @param dto An object containing the ID of the board and the name of the new status
     * @return The updated board
     */
    public Board addStatus(StatusDto dto) {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("board does not exist"));
        board.addStatus(dto.getName());
        return boardRepository.save(board);
    }

    /**
     * This method adds a new type to the board with the given ID.
     *
     * @param dto An object containing the ID of the board and the name of the new type
     * @return The updated board
     */
    public Board addType(AddTypeDto dto) {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("board does not exist"));
        board.addType(dto.getName());
        return boardRepository.save(board);
    }
    /**
     * This method removes the status with the given name from the board with the given ID.
     *
     * @param dto An object containing the ID of the board and the name of the status to be removed
     * @return The updated board
     */
    public Board deleteStatus(StatusDto dto) {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("board does not exist"));
        board.removeStatus(dto.getName());
        return boardRepository.save(board);
    }

    public UserRole getPermission(Long boardId, String userEmail) {
        UserInBoard userInBoard = userInBoardRepository.findByBoardAndUser(boardId, userEmail);
        if (userInBoard == null) {
            throw new IllegalArgumentException("user does not have permission to the board");
        }
        return userInBoard.getRole();
    }
}
