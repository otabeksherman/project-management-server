package projectManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectManagement.entities.board.Board;
import projectManagement.entities.user.User;
import projectManagement.entities.user.UserRole;
import projectManagement.service.BoardService;
import projectManagement.service.ItemService;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    public BoardController(){};

    /*
    Board createBoard(User user){
        //TODO
    }
    Board getBoard(int boardId){
        //TODO
    }
     */

    void deleteBoard(User user){
        //TODO
    }

    void changeNameBoard(User user){
        //TODO
    }

    void updatePermission(String userEmail, UserRole userRoles){
        //TODO
    }

}
