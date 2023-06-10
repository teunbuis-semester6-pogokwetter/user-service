package com.teun.userservice.service;

import com.teun.userservice.models.User;
import com.teun.userservice.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;

    public User findUserByEmail(String email){
        return findUserByEmailInDatabase(email);
    }

    private User findUserByEmailInDatabase(String email){
        return repo.findByEmail(email).orElse(null);
    }
}
