package com.vss.social_webapp.service;

import com.vss.social_webapp.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    void savePost(Post post);

    List<Post> getAllPost();

    List<Post> getAllPostByGroup(String groupCode);

    List<Post> getAllPostByUser(String userEmail);

    Optional<Post> findByCode(String code);

    void updatePost(Post post);

    List<Post> getAllPostAndComment();
}
