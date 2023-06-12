package com.teun.userservice.repo;

import com.teun.userservice.dto.RoleDTO;
import com.teun.userservice.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleDTO, Long> {
    Integer findTableEmpty();
    Optional<RoleDTO> findByName(ERole name);
}
