package projectManagement.controller.gitAuth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/gitAuth")
public class GitAuthController {
    @Value("${github.clientId}")
    String client_id;
    @Value("${github.clientSecret}")
    String client_secret;

    private static final Logger logger = LogManager.getLogger(GitAuthController.class.getName());


    //TODO: implement on front - Get request URL and return code:  https://github.com/login/oauth/authorize?client_id=3832c4b642dd6c67333d&scope=user:email

    /**
     * get git auth code and make http post request and return git token
     *
     * @param code
     * @return token
     */
    @RequestMapping(method = RequestMethod.POST, path = "/getToken")
    public String getTokenFromGit(@RequestParam String code) {
        logger.info("in getTokenFromGit()");
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "https://github.com/login/oauth/access_token";

        //add header to get the response json type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // add body to request
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("code",code);
        map.add("client_id",client_id);
        map.add("client_secret",client_secret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<TokenResponse> response=null;

        try {
            response = restTemplate.postForEntity(resourceUrl, request , TokenResponse.class);
            logger.info("response: "+ response.getBody());
        } catch (Exception e) {
            logger.info("failed to get response");

            System.out.println(e);
        }

        return response.getBody().getAccess_token();

        //TODO: try to change request to string instead TokenResponse
    }


}



//    @RequestMapping(method = RequestMethod.POST, path = "/getToken")
//    public String getTokenFromGit(@RequestParam String code) {
//        logger.info("in getTokenFromGit()");
//
//        RestTemplate restTemplate = new RestTemplate();
//        //String fooResourceUrl = "https://github.com/login/oauth/access_token" + "?code=" + code + "&client_id=" + client_id + "&secret_id=" + secret_id;
//        String resourceUrl = "https://github.com/login/oauth/access_token";
//
//        ResponseEntity<TokenResponse> responseEntity = null;
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
//        map.add("code",code);
//        map.add("client_id",client_id);
//        map.add("client_secret",client_secret);
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//
//        responseEntity = restTemplate.postForEntity(resourceUrl, request , TokenResponse.class);
//        TokenResponse response= responseEntity.getBody();
//        logger.info("response: "+ response);
//
//
//        //TODO: change request to string instead TokenResponse
//
//
//
//        /*headers.set("Accept","Application:json");
//        HttpEntity entity = new HttpEntity(headers);
//
//        Map<String, String> params = new HashMap<>();
//        params.put("code",code);
//        params.put("client_id",client_id);
//        params.put("secret_id",secret_id);
//
//        try {
//            //response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, TokenResponse.class);
//            response = restTemplate.exchange(resourceUrl, HttpMethod.POST, entity, TokenResponse.class, params);
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }*/
//
//        return response.getAccess_token();
//
//        //return response.getBody().getAccess_token();
//    }
