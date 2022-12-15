package projectManagement.entities.board;

import projectManagement.entities.Status;
import projectManagement.entities.item.Item;

public class ItemInBoard {
    Status status;
    Item item;
    Long boardId;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
