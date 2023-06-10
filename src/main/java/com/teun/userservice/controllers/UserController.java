package com.teun.userservice.controllers;

import com.teun.userservice.models.User;
import com.teun.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService service;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        try{
            List<User> users = service.findAll();
            return ResponseEntity.ok().body(users);
        }
        catch (Exception e){

            logger.error("ERROR: " + e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/email/{email}")
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
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            User savedUser = service.saveUser(user);
            return ResponseEntity.ok().body(savedUser);
        }
        catch (Exception e){
            logger.error("ERROR: " + e);
            return ResponseEntity.notFound().build();
        }

    }
}
