package projectManagement.entities.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectManagement.entities.board.Board;
import projectManagement.entities.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String status;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_item_id")
    @JsonIgnore
    private Item parent;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "board_id")
    private Board board;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST})
    Set<Item> subItems;

    @PreRemove
    private void preRemove() {
        subItems.forEach(child -> child.setParent(null));
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;
    private Date dueDate;
    private int importance;
    private String title;
    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Comment> comments;


    public Item(String type, String status, Item parent, Board board, User creator, User assignedTo, Date dueDate, int importance, String title, String description) {
        this.type = type;
        this.status = status;
        this.parent = parent;
        this.board = board;
        this.creator = creator;
        this.assignedTo = assignedTo;
        this.dueDate = dueDate;
        this.importance = importance;
        this.title = title;
        this.description = description;
        this.comments = new HashSet<>();
        this.subItems = new HashSet<>();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
