package projectManagement.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import projectManagement.dto.GitEmailResponse;
import projectManagement.dto.GitTokenResponse;

public class GitAuthUtil {
    private static Logger logger = LogManager.getLogger(GitAuthUtil.class);

    public static String getEmailFromGit(String code, String gitClientId, String gitClientSecret) throws Exception {
        String token = GitAuthUtil.getGitTokenFromCode(code, gitClientId, gitClientSecret);
        logger.debug("git token:" + token);
        if (token == null) {
            throw new Exception("git token is null!");
        }
        String email = GitAuthUtil.getGitEmailFromToken(token);
        logger.debug("git email:" + email);
        return email;
    }

    public static String getGitTokenFromCode(String code, String gitClientId, String gitClientSecret) throws RestClientException {
        String url = "https://github.com/login/oauth/access_token?";
        String token = null;
        // add params to request
        String url_params = url + "client_id=" + gitClientId + "&client_secret=" + gitClientSecret + "&code=" + code;

        ResponseEntity<GitTokenResponse> response = null;
        RestTemplate restTemplate = new RestTemplate();

        //add header to get the response json type
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        try {
            response = restTemplate.exchange(url_params, HttpMethod.POST, entity, GitTokenResponse.class);
            token = response.getBody().getAccess_token();
        } catch (RestClientException e) {
            throw new RestClientException("error: git- http post get token from code");
        }
        return token;
    }

    public static String getGitEmailFromToken(String token) throws RestClientException {
        String url = "https://api.github.com/user/emails";
        String email = null;
        ResponseEntity<GitEmailResponse[]> response = null;
        RestTemplate restTemplate = new RestTemplate();

        //add header with the token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, GitEmailResponse[].class);
            email = response.getBody()[0].getEmail();
        } catch (RestClientException e) {
            throw new RestClientException("error: git- http post get email from token");
        }
        return email;
    }

}
