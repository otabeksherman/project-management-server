package projectManagement.entities.notifictaion;

public enum NotificationType {
    ASSIGN_TO_ME("assignToMe", true),
    STATUS_CHANGED("statusChanged", true),
    COMMENT_ADDED("commentAdded", true),
    DELETED("deleted", true),
    DATA_CHANGED("dataChanged", true),
    USER_ADDED_TO_THE_SYSTEM("userAddedToTheSystem", true);

    private final String text;
    private boolean isTypeActive;

    NotificationType(final String text,boolean isActive) {
        this.text = text;
        this.isTypeActive=isActive;
    }
    public void updateActiveNotification(boolean activeUpdate){
        this.isTypeActive=activeUpdate;
    }

    public boolean isTypeActive() {
        return isTypeActive;
    }

    @Override
    public String toString() {
        return text;
    }


    public String getText() {
        return text;
    }
}
