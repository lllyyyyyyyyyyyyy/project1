package com.vss.social_webapp.repository;

import com.vss.social_webapp.model.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost,Long> {
    List<LikePost> findByPostCode(String postCode);

    Optional<LikePost> findByUserName(String userName);

    List<LikePost> findByPostCodeAndUserName(String postCode, String userName);

    Optional<LikePost> findByPostCodeAndUserNameAndCommentCode(String postCode, String userName, String commentCode);
}
