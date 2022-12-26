package com.vss.social_webapp.dtos;

import com.vss.social_webapp.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDTO {

    private Long id;

    private String code;

    private String userEmail;

    private String userGroupCode;

    private String content;


    private String createdBy;


    private Timestamp createdAt;


    private String updateBy;


    private Timestamp updateAt;

    private boolean isProfilePicture;

    private long sumLike = 0;

    private Long sumComment;

    private Long sumShare;

    private String shareFrom;

    private List<Picture> pictures;

    private User user;

    private UserGroup userGroup;

    private List<CommentPost> comments;

    private List<LikePost> likePosts;

    private Post post;

}
