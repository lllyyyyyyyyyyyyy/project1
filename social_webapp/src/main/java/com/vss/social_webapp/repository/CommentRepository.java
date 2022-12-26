package com.vss.social_webapp.repository;

import com.vss.social_webapp.model.CommentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentPost,Long> {
//    List<CommentPost> findByCode(String code);


    List<CommentPost> findByPostCode(String postCode);

    Optional<CommentPost> findByCode(String commentCode);
}
