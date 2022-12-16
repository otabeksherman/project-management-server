package projectManagement.service;

import projectManagement.repository.UserRepository;

public class AuthService {
    private final UserRepository userRepository;


    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void register(String email,String name, String password){
        //TODO
    }
    public void login(String email, String password){
        //TODO
    }
    /*
    boolean isValidToken(String email, String token){
        //TODO
    }

     */

}
