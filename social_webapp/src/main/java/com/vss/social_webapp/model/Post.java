package com.vss.social_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "postFb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_group_code")
    private String userGroupCode;

    @Column(name = "content")
    private String content;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_at")
    private Timestamp updateAt;


    @Column(name = "profile_picture")
    private boolean isProfilePicture;

    @Column(name = "sum_like")
    private long sumLike = 0;

    @Column(name = "sum_comment")
    private Long sumComment;

    @Column(name = "sum_share")
    private Long sumShare;


    @Transient
    private List<Picture> pictures = new ArrayList<>();
    @Transient
    private User user;

    @Transient
    private UserGroup userGroup;

    @Transient
    List<CommentPost> comments = new ArrayList<>();

    @Transient
    List<LikePost> likePosts = new ArrayList<>();

}
