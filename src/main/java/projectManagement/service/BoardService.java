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

    public List<Board> getAllBoards(String email) {
        return userRepository.findBoards(email);
    }

    public Board getBoard(String email, Long id) throws SQLDataException {
        return boardRepository.findById(id).orElseThrow(SQLDataException::new);
    }

    public Board create(String name, String userEmail) {
        User user = userRepository.findUserByEmail(userEmail);
        Board board = new Board(name, user);
        UserInBoard userInBoard=new UserInBoard(board, user, UserRole.ADMIN);
        return userInBoardRepository.save(userInBoard).getBoard();
    }

    public Board addStatus(StatusDto dto) {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("board does not exist"));
        board.addStatus(dto.getName());
        return boardRepository.save(board);
    }

    public Board addType(AddTypeDto dto) {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("board does not exist"));
        board.addType(dto.getName());
        return boardRepository.save(board);
    }

    public Board deleteStatus(StatusDto dto) {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("board does not exist"));
        board.removeStatus(dto.getName());
        return boardRepository.save(board);
    }

}
