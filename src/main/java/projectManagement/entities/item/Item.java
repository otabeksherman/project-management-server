package projectManagement.entities.item;

import projectManagement.entities.user.User;

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


}
