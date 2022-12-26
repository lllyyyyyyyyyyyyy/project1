package com.vss.social_webapp.dtos;

import com.vss.social_webapp.model.CommentPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private Timestamp createAt;
    private String createBy;
    private Timestamp updateAt;
    private String updateBy;
    private String code;
    private String userEmail;
    private String postCode;
    private String picture;
    private String parentId;
    private String pictureComment;
    private String parentCommentCode;
    private int sumLikeComment;
    private List<CommentPost> commentPosts;
}
