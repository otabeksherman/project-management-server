package projectManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.entities.Operation;
import projectManagement.entities.board.Board;
import projectManagement.entities.user.User;
import projectManagement.entities.user.UserInBoard;
import projectManagement.entities.user.UserRole;
import projectManagement.repository.BoardRepository;
import projectManagement.repository.UserInBoardRepository;
import projectManagement.repository.UserRepository;

import java.util.List;

@Service
public class PermissionService {
    private static Logger logger = LogManager.getLogger(PermissionService.class);
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final UserInBoardRepository userInBoardRepository;

    public PermissionService(UserRepository userRepository, BoardRepository boardRepository, UserInBoardRepository userInBoardRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.userInBoardRepository = userInBoardRepository;
    }

    /**
     * Checks if the user is authorized for the required operation.
     * this method get the users of the board, check what is the role of the user,
     * and check if this role allows for the operation
     *
     * @param boardId
     * @param userEmail
     * @param operation
     * @return true if the user has permissions for the required operation, else false.
     */
    public boolean isAuthorized(Long boardId, String userEmail, Operation operation) {
        logger.info("in isAuthorized():");
        UserInBoard boardUser = userInBoardRepository.findByBoardAndUser(boardId, userEmail);

        if (boardUser == null) {
            return false;
        }
        UserRole userRole = boardUser.getRole();

        List<UserRole> usersRoleInBoard = operation.getUserRole(); //get the allows roles for this operation

        return ((usersRoleInBoard.contains(userRole)));

    }

    /**
     * add/change role to user to board
     *
     * @param boardId
     * @param userEmail
     * @param role
     */
    public void updateUserRole(Long boardId, String userEmail, UserRole role) {
        logger.info("in updateUserRole():");
        UserInBoard boardUser = userInBoardRepository.findByBoardAndUser(boardId, userEmail);
        if (boardUser == null) {
            addUserRole(boardId, userEmail, role);
        } else {
            boardUser.setRole(role);
            userInBoardRepository.save(boardUser);
        }
    }

    /**
     * add role to user to board
     *
     * @param boardId
     * @param userEmail
     * @param role
     */
    public void addUserRole(Long boardId, String userEmail, UserRole role) {
        logger.info("in addUserRole():");
        Board board = boardRepository.getReferenceById(boardId);
        User user = userRepository.findUserByEmail(userEmail);
        UserInBoard userInBoard = new UserInBoard(board, user, role);
        userInBoardRepository.save(userInBoard);
    }

    /**
     * delete role to user to board
     *
     * @param boardId
     * @param userEmail
     * @param role
     */
    public void deleteUerRole(Long boardId, String userEmail, UserRole role) {
        UserInBoard boardUser = userInBoardRepository.findByBoardAndUser(boardId, userEmail);
        if (boardUser != null) {
            userInBoardRepository.delete(boardUser);
        }
    }
}