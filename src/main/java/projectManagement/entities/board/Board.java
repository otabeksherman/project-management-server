package projectManagement.entities.board;

import projectManagement.entities.Status;

import java.util.Set;

public class Board {
    Long id;
    String name;
    Set<String> types;
    Set<Status> statuses;
    ItemInBoard items;

    public Board(String name, Set<String> types, Set<Status> statuses, ItemInBoard items) {
        this.name = name;
        this.types = types;
        this.statuses = statuses;
        this.items = items;
    }

    public void addType(String type){
        types.add(type);
    }

    public Set<String> getTypes() {
        return types;
    }

    public Set<Status> getStatuses() {
        return statuses;
    }

    public ItemInBoard getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }
}
