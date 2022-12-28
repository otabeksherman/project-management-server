package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.dto.ShareBoardDto;
import projectManagement.entities.board.Board;
import projectManagement.entities.user.User;
import projectManagement.entities.user.UserInBoard;
import projectManagement.repository.BoardRepository;
import projectManagement.repository.UserInBoardRepository;
import projectManagement.repository.UserRepository;

import java.sql.SQLDataException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserInBoardService {
    private static Logger logger = LogManager.getLogger(UserInBoardService.class);
    private final UserInBoardRepository userInBoardRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<String> findByBoard(Long boardId) {
        return userInBoardRepository.findByBoard_Id(boardId).stream().map(userInBoard -> userInBoard.getUser().getEmail()).collect(Collectors.toList());
    }

    public UserInBoard share(ShareBoardDto dto) throws SQLDataException {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("board does not exist"));
        User user = userRepository.findUserByEmail(dto.getUserEmail());
        if (user == null) {
            throw new SQLDataException(String.format("Email %s is not exists in users table", dto.getUserEmail()));
        }
        UserInBoard userInBoard = new UserInBoard(board, user, dto.getRole());
        return userInBoardRepository.save(userInBoard);
    }
}