package projectManagement.entities.user;

import projectManagement.entities.board.Board;
import projectManagement.entities.notifictaion.Notification;

import java.util.List;

public class User {
    Long id;
    String email;
    String password;
    List<Board> boards;
    List<Notification> notifications;
    Boolean emailNotify;
}
