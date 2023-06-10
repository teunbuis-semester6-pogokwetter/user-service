package com.teun.userservice.controllers;

import com.teun.userservice.models.User;
import com.teun.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService service;



    @GetMapping("{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable(value = "email") String email ){
        try{
            User user = service.findUserByEmail(email);
            return ResponseEntity.ok().body(user);
        }
        catch (Exception e){
            logger.error("ERROR: " + e);
            return ResponseEntity.notFound().build();
        }

    }
}
