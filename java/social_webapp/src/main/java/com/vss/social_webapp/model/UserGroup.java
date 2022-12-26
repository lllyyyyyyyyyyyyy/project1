package com.vss.social_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users_groups_roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "group_code")
    private String groupCode;

    @Column(name = "role_code")
    private String roleCode;

    @Transient
    private Group group;

    @Transient
    private List<Post> posts;
}
