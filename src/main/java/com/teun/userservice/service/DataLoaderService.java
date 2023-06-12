package com.teun.userservice.service;

import com.teun.userservice.dto.RoleDTO;
import com.teun.userservice.enums.ERole;
import com.teun.userservice.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoaderService implements ApplicationRunner {
    @Autowired
    private RoleRepo roleRepo;

    public void run(ApplicationArguments args) {
        if(roleRepo.findTableEmpty() <= 0)
        {
            roleRepo.save(new RoleDTO(ERole.ROLE_SUPER));
            roleRepo.save(new RoleDTO(ERole.ROLE_MODERATOR));
            roleRepo.save(new RoleDTO(ERole.ROLE_USER));
            roleRepo.save(new RoleDTO(ERole.ROLE_ADMIN));
        }
    }
}
