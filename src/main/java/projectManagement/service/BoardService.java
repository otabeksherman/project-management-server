package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.controller.AuthenticationController;
import projectManagement.entities.board.Board;
import projectManagement.repository.BoardRepository;
import projectManagement.repository.UserRepository;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BoardService {

    private static Logger logger = LogManager.getLogger(BoardService.class);
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public List<Board> getAllBoards(String email) {
        return userRepository.findBoards(email);
    }

    public Board getBoard(String email, Long id) throws SQLDataException {
        return boardRepository.findById(id).orElseThrow(SQLDataException::new);
    }
}
