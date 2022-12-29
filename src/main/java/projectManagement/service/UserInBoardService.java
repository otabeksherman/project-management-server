package projectManagement.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.dto.ShareBoardDto;
import projectManagement.entities.board.Board;
import projectManagement.entities.user.User;
import projectManagement.entities.user.UserInBoard;
import projectManagement.entities.user.UserRole;
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

    /**

     This method returns a list of emails of users that are associated with a board with the given boardId.
     @param boardId the id of the board to find the users for
     @return a list of emails of users that are associated with the given board
     */
    public List<String> findByBoard(Long boardId) {
        return userInBoardRepository.findByBoard_Id(boardId).stream().map(userInBoard -> userInBoard.getUser().getEmail()).collect(Collectors.toList());
    }

    /**

     This method creates a new UserInBoard entity and associates it with the board and user specified in the ShareBoardDto object.
     @param dto a ShareBoardDto object containing the information about the board and user to associate
     @return the UserInBoard entity that was created
     @throws SQLDataException if the email specified in the dto object is not found in the users table
     */
    public UserInBoard share(ShareBoardDto dto) throws SQLDataException {
        Board board = boardRepository.findById(dto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("board does not exist"));
        User user = userRepository.findUserByEmail(dto.getUserEmail());
        if (user == null) {
            throw new SQLDataException(String.format("Email %s is not exists in users table", dto.getUserEmail()));
        }
        UserRole userRole=UserRole.valueOf(dto.getRole());
        UserInBoard userInBoard = new UserInBoard(board, user, userRole);
        return userInBoardRepository.save(userInBoard);
    }
}