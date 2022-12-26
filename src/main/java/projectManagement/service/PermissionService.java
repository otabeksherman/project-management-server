package projectManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import projectManagement.entities.Operation;
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
     * @param boardId
     * @param userId
     * @param operation
     * @return  true if the user has permissions for the required operation, else false.
     */
    public boolean isAuthorized(Long boardId, Long userId, Operation operation){
        logger.info("in isAuthorized():");
        List<UserInBoard> boardUsers=userInBoardRepository.findByBoardAndUser(boardId,userId); //get the users of the board

        if(boardUsers.isEmpty()){
            return false;
        }

        List<UserRole> usersRoleInBoard= operation.getUserRole(); //get the allows roles for this operation
        User user=userRepository.getReferenceById(userId);

        if(boardUsers.contains(user)) {
            UserRole userRole = boardUsers.get(boardUsers.indexOf(user)).getRole(); //check the role of the user on the board
            return (usersRoleInBoard.contains(userRole));
        }
        return false;
    }

}
