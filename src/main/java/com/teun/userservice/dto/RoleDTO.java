package com.teun.userservice.dto;



import com.teun.userservice.enums.ERole;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class RoleDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roles_id")
    private Integer rolesId;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
    public RoleDTO() {
    }
    public RoleDTO(ERole name) {
        this.name = name;
    }

    public Integer getRolesId() {
        return rolesId;
    }

    public void setRolesId(Integer rolesId) {
        this.rolesId = rolesId;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
