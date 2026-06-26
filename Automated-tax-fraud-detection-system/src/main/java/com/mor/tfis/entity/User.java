package com.mor.tfis.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Column(unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "department_id")
    private java.util.UUID departmentId;

    @Column(name = "mfa_enabled")
    private Boolean mfaEnabled;

    @Column(name = "public_key_cert", columnDefinition = "TEXT")
    private String publicKeyCert;

    @Column(name = "last_login")
    private java.time.LocalDateTime lastLogin;

    public enum Role {
        INTELLIGENCE_OFFICER, INVESTIGATION_OFFICER, TEAM_LEADER, PROCESS_OWNER, DIRECTOR, ADMIN
    }
}