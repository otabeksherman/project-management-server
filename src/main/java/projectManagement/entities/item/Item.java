package projectManagement.entities.item;

import projectManagement.entities.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Item {
    Long id;
    String type;
    Item parent;
    User creator;
    User assignedTo;
    Date dueDate;
    int importance;
    String title;
    String description;
    List<Comment> comments;


    public Item(String type, Item parent, User creator, User assignedTo, Date dueDate, int importance, String title, String description) {
        this.type = type;
        this.parent = parent;
        this.creator = creator;
        this.assignedTo = assignedTo;
        this.dueDate = dueDate;
        this.importance = importance;
        this.title = title;
        this.description = description;
        this.comments = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
