package projectManagement.entities.user;

import projectManagement.entities.board.Board;


public class Authorization {
    private int id;

    private Board board;

    private User user;


    private UserRole permission;

    public Authorization() {
    }

    public Authorization(Board board, User user, UserRole permission) {
        this.board = board;
        this.user = user;
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public User getUser() {
        return user;
    }

    public UserRole getPermission() {
        return permission;
    }

    public void setPermission(UserRole permission) {
        this.permission = permission;
    }
}
