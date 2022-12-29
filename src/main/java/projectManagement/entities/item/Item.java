package projectManagement.entities.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
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
    @JsonIgnore
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
    public static class Builder {
        private String type;
        private String status;
        private Item parent;
        private Board board;
        private User creator;
        private Set<Item> subItems;
        private User assignedTo;
        private Date dueDate;
        private int importance;
        private String title;
        private String description;
        private Set<Comment> comments;

        public Builder() {
        }
        public Builder type(String type){
            this.type=type;
            return this;
        }
        public Builder status(String status){
            this.status=status;
            return this;
        }
        public Builder parent(Item parent){
            this.parent=parent;
            return this;
        }
        public Builder board(Board board){
            this.board=board;
            return this;
        }
        public Builder creator(User creator){
            this.creator=creator;
            return this;
        }
        public Builder assignedTo(User assignedTo){
            this.assignedTo=assignedTo;
            return this;
        }
        public Builder dueDate(Date dueDate){
            this.dueDate=dueDate;
            return this;
        }
        public Builder importance(int importance){
            this.importance=importance;
            return this;
        }
        public Builder title(String title){
            this.title=title;
            return this;
        }
        public Builder description(String description){
            this.description=description;
            return this;
        }
        public Item build(){
            return new Item(this);
        }
    }

    public Item(Builder builder){
        this.type = builder.type;
        this.status = builder.status;
        this.parent = builder.parent;
        this.board = builder.board;
        this.creator = builder.creator;
        this.assignedTo = builder.assignedTo;
        this.dueDate = builder.dueDate;
        this.importance = builder.importance;
        this.title = builder.title;
        this.description = builder.description;
    }



    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
