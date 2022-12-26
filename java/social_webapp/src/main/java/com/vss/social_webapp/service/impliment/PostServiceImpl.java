package com.vss.social_webapp.service.impliment;

import com.vss.social_webapp.dtos.CommentDTO;
import com.vss.social_webapp.dtos.PostDTO;
import com.vss.social_webapp.model.*;
import com.vss.social_webapp.repository.*;
import com.vss.social_webapp.service.CommentService;
import com.vss.social_webapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Autowired
    LikePostRepository likePostRepository;

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> getAllPost() {

        List<Post> posts = postRepository.findAll();
        for (Post post:posts){
                getAllPostByPost(post);

        }

//        List<Post> posts = postRepository.findAll();
//        for (Post post:posts){
//            if(post.getShareFrom().isEmpty()){
//                getAllPostByPost(post);
//            }
//            else {
//                Optional<Post> post1 = postRepository.findByCode(post.getCode());
//                PostDTO postDTO = copyPostFromPostToPostDTO(post1.get());
//                post.setPost(copyFromPostDTOToPost(postDTO));
//                getAllPostByPost(post);
//            }
//
//
//        }
        return posts;
    }

    private PostDTO copyPostFromPostToPostDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setCode(post.getCode());
        postDTO.setUserEmail(post.getUserEmail());
        postDTO.setUserGroupCode(post.getUserGroupCode());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedBy(post.getCreatedBy());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUpdateAt(post.getUpdateAt());
        postDTO.setUpdateBy(post.getUpdateBy());
        postDTO.setProfilePicture(post.isProfilePicture());
        postDTO.setSumLike(post.getSumLike());
        postDTO.setSumComment(post.getSumComment());
        postDTO.setSumShare(post.getSumShare());
        return postDTO;
    }

    private Post copyFromPostDTOToPost(PostDTO postDTO){
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setCode(postDTO.getCode());
        post.setUserEmail(postDTO.getUserEmail());
        post.setUserGroupCode(postDTO.getUserGroupCode());
        post.setContent(postDTO.getContent());
        post.setCreatedBy(postDTO.getCreatedBy());
        post.setCreatedAt(postDTO.getCreatedAt());
        post.setUpdateAt(postDTO.getUpdateAt());
        post.setUpdateBy(postDTO.getUpdateBy());
        post.setProfilePicture(postDTO.isProfilePicture());
        post.setSumLike(postDTO.getSumLike());
        post.setSumComment(postDTO.getSumComment());
        post.setSumShare(postDTO.getSumShare());
        return post;
    }
    private Post getAllPostByPost(Post post){
        List<Picture> pictures = pictureRepository.findAllByPostCode(post.getCode());
        if(pictures.isEmpty()){
            post.setPictures(null);
        }
        else {
            post.setPictures(pictures);
        }
        User user = userRepository.findByEmail(post.getUserEmail()).get();
        if(user.isEnabled()){
            post.setUser(user);
        }
        else {
            post.setUser(null);
        }
        Optional<UserGroup> userGroup = userGroupRepository.findByGroupCodeAndUserEmail(post.getUserGroupCode(), post.getUserEmail());
        if(userGroup.isPresent()){
            Optional<Group> group = groupRepository.findByCode(userGroup.get().getGroupCode());
            if(group.isPresent()){
                userGroup.get().setGroup(group.get());
            }
            else userGroup.get().setGroup(group.get());
            post.setUserGroup(userGroup.get());
        }
        else post.setUserGroup(null);
//            List<CommentPost> commentPosts = commentRepository.findByPostCode(post.getCode());
//            if(!commentPosts.isEmpty()) post.setComments(commentPosts);
//            else post.setComments(null);

        List<LikePost> likePosts = likePostRepository.findByPostCode(post.getCode());
        if(likePosts.isEmpty()) post.setLikePosts(null);
        else{
            post.setLikePosts(likePosts);
        }
        return post;
    }


    @Override
    public List<Post> getAllPostByGroup(String groupCode) {
        List<Post> posts = postRepository.findByUserGroupCode(groupCode);
        if(posts.isEmpty()) return null;
        else{
            for(Post post:posts){
                List<Picture> pictures = pictureRepository.findAllByPostCode(post.getCode());
                if(!pictures.isEmpty()){
                    post.setPictures(pictures);
                }
                else post.setPictures(null);

                User user = userRepository.findByEmail(post.getUserEmail()).get();
                if(user.isEnabled()){
                    post.setUser(user);
                }
                else post.setUser(null);

                List<CommentPost> commentPosts = commentRepository.findByPostCode(post.getCode());
                if(commentPosts.isEmpty()) post.setComments(null);
                else post.setComments(commentPosts);
            }
            return posts;
        }
    }

    @Override
    public List<Post> getAllPostByUser(String userEmail) {
        List<Post> posts = postRepository.findPostFromUser(userEmail);
        if(posts.isEmpty()) return null;
        else {
            for(Post post:posts){
                List<Picture> pictures = pictureRepository.findAllByPostCode(post.getCode());
                if(pictures.isEmpty())  post.setPictures(null);
                else  post.setPictures(pictures);


                User user = userRepository.findByEmail(post.getUserEmail()).get();
                if(user.isEnabled()) post.setUser(user);
                else post.setUser(null);

                List<CommentPost> commentPosts = commentRepository.findByPostCode(post.getCode());
                if(commentPosts.isEmpty()) post.setComments(null);
                else post.setComments(commentPosts);

            }
            return posts;
        }
    }

    @Override
    public Optional<Post> findByCode(String code) {
        Optional<Post> post = postRepository.findByCode(code);
        return post;
    }

    @Override
    public void updatePost(Post post) {
        Optional<Post> post1 = postRepository.findByCode(post.getCode());

        if(post1.isPresent()){
            post1.get().setContent(post.getContent());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            post1.get().setUpdateAt(timestamp);
            post1.get().setUpdateBy(post.getUserEmail());
        }
        postRepository.save(post1.get());
    }

    @Override
    public List<Post> getAllPostAndComment() {
        List<Post> posts = postRepository.findAll();
        for (Post post:posts){
            List<Picture> pictures = pictureRepository.findAllByPostCode(post.getCode());
            if(pictures.isEmpty()){
                post.setPictures(null);
            }
            else {
                post.setPictures(pictures);
            }
            User user = userRepository.findByEmail(post.getUserEmail()).get();
            if(user.isEnabled()){
                post.setUser(user);
            }
            else {
                post.setUser(null);
            }
            Optional<UserGroup> userGroup = userGroupRepository.findByGroupCodeAndUserEmail(post.getUserGroupCode(), post.getUserEmail());
            if(userGroup.isPresent()){
                Optional<Group> group = groupRepository.findByCode(userGroup.get().getGroupCode());
                if(group.isPresent()){
                    userGroup.get().setGroup(group.get());
                }
                else userGroup.get().setGroup(group.get());
                post.setUserGroup(userGroup.get());
            }
            else post.setUserGroup(null);
            List<CommentDTO> commentDTOS = commentService.findCommentChildByPostCode(post.getCode());
            List<CommentPost> commentPosts = commentRepository.findByPostCode(post.getCode());
            if(!commentPosts.isEmpty()){
                for(CommentPost commentPost:commentPosts){
                    List<CommentPost> commentPostList = new ArrayList<>();
                    for(CommentDTO commentDTO:commentDTOS){
                        if(commentPost.getCode().equals(commentDTO.getParentCommentCode())){

                            CommentPost commentPost1 = new CommentPost();
                            commentPost1.setId(commentDTO.getId());
                            commentPost1.setContent(commentDTO.getContent());
                            commentPost1.setCreateAt(commentDTO.getCreateAt());
                            commentPost1.setCreateBy(commentDTO.getCreateBy());
                            commentPost1.setUpdateAt(commentDTO.getUpdateAt());
                            commentPost1.setUpdateBy(commentDTO.getUpdateBy());
                            commentPost1.setCode(commentDTO.getCode());
                            commentPost1.setUserEmail(commentDTO.getUserEmail());
                            commentPost1.setPostCode(commentDTO.getPostCode());
                            commentPost1.setPicture(commentDTO.getPicture());
                            commentPost1.setPictureComment(commentDTO.getPictureComment());
                            commentPost1.setParentCommentCode(commentDTO.getParentCommentCode());
                            commentPost1.setSumLikeComment(commentDTO.getSumLikeComment());
                            commentPostList.add(commentPost1);
                        }
                    }
                    commentPost.setCommentPosts(commentPostList);
                }
                post.setComments(commentPosts);
            }
            else post.setComments(null);

            List<LikePost> likePosts = likePostRepository.findByPostCode(post.getCode());
            if(likePosts.isEmpty()) post.setLikePosts(null);
            else{
                post.setLikePosts(likePosts);
            }

        }
        return posts;
    }
}
