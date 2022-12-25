package projectManagement.dto;

public class GitEmailResponse {
    private String email;
    private boolean primary;
    private boolean verified;
    private String visibility;

    public String getEmail() {
        return email;
    }
}