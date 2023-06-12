package com.teun.userservice.repo;

import com.teun.userservice.dto.AuthDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepo extends JpaRepository<AuthDTO, Long> {
//    @Query("SELECT a FROM auth a WHERE a.username = ?1")
    Optional<AuthDTO> findByUsername(String username);

//    @Query("SELECT a FROM auth a WHERE a.authId = ?1")
    Optional<AuthDTO> findByAuthId(UUID id);

    Optional<AuthDTO> findByEmail(String email);

    Boolean existsByEmail(String email);
}
