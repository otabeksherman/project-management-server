package projectManagement.entities.notifictaion;

import javax.persistence.Entity;
import javax.persistence.Table;


public enum NotificationType {
    ASSIGN_TO_ME("assignToMe"),
    STATUS_CHANGED("statusChanged"),
    COMMENT_ADDED("commentAdded"),
    DELETED("deleted"),
    DATA_CHANGED("dataChanged"),
    USER_ADDED_TO_THE_SYSTEM("userAddedToTheSystem");

    private final String text;
    private boolean isTypeActive;

    NotificationType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public String getText() {
        return text;
    }
}
