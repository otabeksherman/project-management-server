package projectManagement.repository;


import projectManagement.entities.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    int deleteById(int id);

    int updateUserNameById(int id, String name);


    int updateUserEmailById(int id, String email);


    int updateUserPasswordById(int id, String password);


    int updateUserEnabledById(int id, Boolean enabled);
}
