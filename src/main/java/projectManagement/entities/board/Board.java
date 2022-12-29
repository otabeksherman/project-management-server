package projectManagement.entities.board;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import projectManagement.entities.item.Item;
import projectManagement.entities.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "boards")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    @Column
    @ElementCollection(targetClass = String.class)
    Set<String> types;
    @Column
    @ElementCollection(targetClass = String.class)
    Set<String> statuses;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Item> items;

    public Board(String name, User user) {
        this.name = name;
        this.user = user;
        this.types = new HashSet<>();
        this.statuses = new HashSet<>();
        this.items = new HashSet<>();
    }

    public void addStatus(String status) {
        this.statuses.add(status);
    }

    public void addType(String type) {
        this.types.add(type);
    }

    public void removeStatus(String status) {
        this.items.removeIf(item -> item.getStatus().equals(status));
        this.statuses.remove(status);
    }
}
