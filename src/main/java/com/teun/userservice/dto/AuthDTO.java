package com.teun.userservice.dto;

import jakarta.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity(name="auth")
public class AuthDTO {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name = "auth_id")
    private UUID authId;

    @NotBlank
    @Size(max = 50)
    @Email
    private String username;
    @NotBlank
    @Size(max = 120)
    private String password;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private String password_reset_token;
    private String creation_of_token_date;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "auth_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<RoleDTO> roles = new HashSet<>();
    public AuthDTO() {
    }
    public AuthDTO(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public UUID getAuthId() {
        return authId;
    }

    public void setAuthId(UUID authId) {
        this.authId = authId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_reset_token() {
        return password_reset_token;
    }

    public void setPassword_reset_token(String password_reset_token) {
        this.password_reset_token = password_reset_token;
    }

    public String getCreation_of_token_date() {
        return creation_of_token_date;
    }

    public void setCreation_of_token_date(String creation_of_token_date) {
        this.creation_of_token_date = creation_of_token_date;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}
