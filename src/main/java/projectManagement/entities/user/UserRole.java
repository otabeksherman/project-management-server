package projectManagement.entities.user;

public enum UserRole {
    ADMIN("admin"),
    LEADER("leader"),
    USER("user");

    private final String text;
    UserRole(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
