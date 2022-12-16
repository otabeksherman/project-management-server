package projectManagement.entities.board;

import projectManagement.entities.Status;
import projectManagement.entities.item.Item;

public class ItemInBoard {
    private Status status;
    private Item item;
    private Long boardId;


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
