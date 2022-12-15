package projectManagement.entities.board;

import projectManagement.entities.Status;

import java.util.Set;

public class Board {
    Long id;
    String name;
    Set<String> types;
    Set<Status> statuses;
    ItemInBoard items;


}
