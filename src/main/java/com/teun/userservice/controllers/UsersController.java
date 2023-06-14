package com.teun.userservice.controllers;

import com.teun.userservice.models.User;
import com.teun.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    @Autowired
    private UserService service;

    @GetMapping()
    public String createUser(Authentication authentication) {
        User user = new User();
        UserDetails userDetails =  (UserDetails) authentication.getDetails();
        user.setUserName(userDetails.getUsername());
        user.setAppId("GitHub");
        // Use the retrieved user details to create a user in your system
        // ...
        return "User created successfully";
    }
    @GetMapping("/all")
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
    @PostMapping()
    public ResponseEntity deleteUser(@RequestBody Long userId){
        try{
            service.deleteUser(userId);
            return ResponseEntity.ok().build();
        }
        catch(Exception exception){
            logger.error("Error: " + exception);
            return ResponseEntity.badRequest().build();
        }
    }
}
