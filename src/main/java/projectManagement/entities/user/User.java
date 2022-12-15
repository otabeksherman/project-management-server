package projectManagement.entities.user;

import projectManagement.entities.board.Board;
import projectManagement.entities.notifictaion.Notification;

import java.util.ArrayList;
import java.util.List;

public class User {
    Long id;
    String email;
    String password;
    List<Board> boards;
    List<Notification> notifications;
    Boolean emailNotify;
    private boolean enabled;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.emailNotify = true; //default is true
        this.boards= new ArrayList<>();
        this.notifications=new ArrayList<>();
        this.enabled = false;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Boolean getEmailNotify() {
        return emailNotify;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmailNotify(Boolean emailNotify) {
        this.emailNotify = emailNotify;
    }
    public void setEnabled(boolean b) {
        this.enabled = true;
    }


    public boolean isEnabled() {
        return enabled;
    }


}
