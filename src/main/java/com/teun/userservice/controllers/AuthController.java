package com.teun.userservice.controllers;


import com.teun.userservice.dto.AuthDTO;
import com.teun.userservice.dto.RoleDTO;
import com.teun.userservice.enums.ERole;
import com.teun.userservice.models.*;
import com.teun.userservice.repo.AuthRepo;
import com.teun.userservice.repo.RoleRepo;
import com.teun.userservice.service.AuthService;
import com.teun.userservice.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AuthService authLogic;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthRepo authRepository;

    @Autowired
    RoleRepo roleRepository;
    @Autowired
    JwtUtils jwtUtils;
    private final RabbitTemplate rabbitTemplate;

    public AuthController (RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @PostMapping("/signing")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginModel loginRequest) {
        System.out.println("test");
        if(loginRequest.getUsername() != null)
        {
            loginRequest.setEmail(loginRequest.getUsername());
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);


        AuthModel authModel = (AuthModel) authentication.getPrincipal();
        List<String> roles = authModel.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseModel(jwt,
                authModel.getUserId(),
                authModel.getUsername(),
                authModel.getEmail(),
                roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpModel signUpRequest) {
        if (authRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponseModel("Error: Username is already taken!"));
        }
        if (authRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponseModel("Error: Email is already in use!"));
        }
        // Create new user's account
        AuthDTO user = new AuthDTO(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getUsername());
        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleDTO> roles = new HashSet<>();
        if (strRoles == null) {
            RoleDTO role = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(role);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "super":
                        RoleDTO role1 = roleRepository.findByName(ERole.ROLE_SUPER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(role1);
                        break;
                    case "moderator":
                        RoleDTO role2 = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(role2);
                        break;
                    case "admin":
                        RoleDTO role3 = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(role3);
                        break;
                    default:
                        RoleDTO role4 = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(role4);
                }
            });
        }
        user.setRoles(roles);
        authRepository.save(user);
        return ResponseEntity.ok(new MessageResponseModel("User registered successfully!"));
    }
    @RequestMapping(value = "/removeAccount", method = RequestMethod.POST)
    public ResponseEntity<?> removeAccount(@RequestBody RemovedModel remove){
        logger.info("Received request to create offer: {}", remove.getId());
        rabbitTemplate.convertAndSend("", "q.deleteInfoTrade", remove.getId().toString());
        rabbitTemplate.convertAndSend("", "q.deleteInfoMessage", remove.getId().toString());
        rabbitTemplate.convertAndSend("", "q.deleteReadInfoMessage", remove.getId().toString());
        authLogic.removeAccount(remove.getId());
        return ResponseEntity.ok().body(remove.getId());
    }
    @GetMapping("/kube")
    public ResponseEntity<?> kube() {
        return ResponseEntity.ok(new MessageResponseModel("trashed"));
    }
}
