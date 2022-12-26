package com.vss.social_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "like_post")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "comment_code")
    private String commentCode;
}
