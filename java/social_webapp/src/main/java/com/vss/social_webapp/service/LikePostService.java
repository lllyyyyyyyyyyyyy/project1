package com.vss.social_webapp.service;

import com.vss.social_webapp.model.LikePost;

import java.util.List;
import java.util.Optional;

public interface LikePostService {
    void saveLikePost(LikePost likePost);

    boolean checkLikePost(String userName);

    List<LikePost> findByPostCode(String postCode);

    LikePost findByUserName(String userName);

    boolean checkIfUserLikedPost(String userName, String postCode);

//    List<LikePost> findByUserNameAndPostCode(String userName, String postCode);

    List<LikePost> getAllLikePost();

    boolean checkLikeComment(String userName, String postCode, String commentCode);

    Optional<LikePost> findByUserNameAndPostCodeAndCommentCode(String userName, String postCode, String commentCode);

    List<LikePost> findByUserNameAndPostCode(String userName, String postCode);

//    List<LikePost> findByUserNameAndPostCodeAndCommentCode(String userName, String postCode);
}
