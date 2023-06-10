package com.teun.userservice.service;

import com.teun.userservice.models.User;
import com.teun.userservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    public User findUserByEmail(String email){
        return findUserByEmailInDatabase(email);
    }
    public User saveUser(User user) {
        return saveUserToDatabase(user);
    }
    public List<User> findAll() {
        return findAllFromDatabase();
    }
    private User findUserByEmailInDatabase(String email){
        return repo.findByEmail(email).orElse(null);
    }

    private User saveUserToDatabase(User user){
        return repo.save(user);
    }
    private List<User> findAllFromDatabase(){
        return repo.findAll();
    }

}
