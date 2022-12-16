package projectManagement.entities;


public class LoginData {
    private Long userId;
    private String token;

    public LoginData(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}
