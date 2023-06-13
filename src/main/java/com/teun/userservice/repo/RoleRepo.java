package com.teun.userservice.repo;

import com.teun.userservice.dto.RoleDTO;
import com.teun.userservice.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleDTO, Long> {

    @Query("SELECT count(*) FROM auth a")
    Integer findTableEmpty();
    Optional<RoleDTO> findByName(ERole name);
}
