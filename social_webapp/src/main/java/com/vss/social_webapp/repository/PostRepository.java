package com.vss.social_webapp.repository;

import com.vss.social_webapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserGroupCode(String userGroupCode);

//    List<Post> findByUserEmail(String userEmail);

    @Query(value = "SELECT * FROM postfb WHERE user_email = ?1 AND user_group_code is null",nativeQuery = true)
    List<Post> findPostFromUser(String userEmail);

    Optional<Post> findByCode(String code);
}
