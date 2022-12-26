package com.vss.social_webapp.service;

import com.vss.social_webapp.dtos.CommentDTO;
import com.vss.social_webapp.model.CommentPost;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    void saveComment(CommentPost commentPost);


    Optional<CommentPost> findByCommentCode(String commentCode);


    List<CommentDTO> findCommentChildByPostCode(String postCode);
}
