package com.vss.social_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment_post")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "code")
    private String code;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "postCode")
    private String postCode;

    @Column(name = "picture")
    private String picture;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "picture_comment")
    private String pictureComment;

    @Column(name = "parent_comment_code")
    private String parentCommentCode;

    @Column(name = "sum_like_comment")
    private int sumLikeComment = 0;

    @Transient
    List<CommentPost> commentPosts = new ArrayList<>();

}
