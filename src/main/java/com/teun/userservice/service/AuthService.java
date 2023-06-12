package com.teun.userservice.service;

import com.teun.userservice.dto.AuthDTO;
import com.teun.userservice.models.AuthModel;
import com.teun.userservice.repo.AuthRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    AuthRepo authRepo;
    public Boolean createAccount(AuthModel auth){
        try{
            ModelMapper modelMapper = new ModelMapper();
            AuthDTO authDTO = modelMapper.map(auth, AuthDTO.class);
            authRepo.save(authDTO);
            return true;
        }
        catch(Exception e) {
            return false;
        }

    }
    public AuthModel getAccount(AuthModel auth){
        AuthDTO authDTO = authRepo.findByUsername(auth.getUsername()).orElse(null);
        ModelMapper modelMapper = new ModelMapper();
        auth = modelMapper.map(authDTO, AuthModel.class);
        return auth;
    }

    public Boolean removeAccount(UUID id){
        AuthDTO authDTO = authRepo.findByAuthId(id).orElse(null);
        authRepo.delete(authDTO);
        return true;
    }
}
