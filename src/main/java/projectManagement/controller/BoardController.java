package projectManagement.controller;


import projectManagement.entities.user.User;
import projectManagement.entities.user.UserRole;
import projectManagement.service.BoardService;


public class BoardController {
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
