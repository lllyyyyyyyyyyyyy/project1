package com.vss.social_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users_roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "user_email")
    private String userEmail;

    @Transient
    private Role role;
}
