package projectManagement.controller.response;

public class GitEmailResponse {
    private String email;
    private boolean primary;
    private boolean verified;
    private String visibility;

    public String getEmail() {
        return email;
    }

    public boolean getPrimary() {
        return primary;
    }
}