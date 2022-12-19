package projectManagement.dto;

public class GitTokenResponse {
    private String access_token;
    private String token_type;
    private String scope;

//    public TokenResponse(String access_token, String token_type, String scope) {
//        this.access_token = access_token;
//        this.token_type = token_type;
//        this.scope = scope;
//    }
//    public TokenResponse() {
//    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getScope() {
        return scope;
    }
}
