package projectManagement.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projectManagement.entities.board.Board;
import projectManagement.entities.user.User;
import projectManagement.entities.user.UserRole;
import projectManagement.repository.BoardRepository;

@Service
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;

    private static final Logger logger = LogManager.getLogger(BoardService.class.getName());

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /*
    Board createBoard(String email){
        //TODO
    }

    Board getBoard(Long boardId){
        //TODO
    }
     */
    void deleteBoard(String email){
        //TODO
    }
    void changeNameBoard(String email, String newName){
        //TODO
    }
    void updatePermission(String email, UserRole userRole){
        //TODO
    }

}
