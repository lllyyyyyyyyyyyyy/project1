package com.vss.social_webapp.controller;

import com.vss.social_webapp.common.CommonConst;
import com.vss.social_webapp.model.CommentPost;
import com.vss.social_webapp.model.LikePost;
import com.vss.social_webapp.model.Post;
import com.vss.social_webapp.model.User;
import com.vss.social_webapp.service.CommentService;
import com.vss.social_webapp.service.LikePostService;
import com.vss.social_webapp.service.PostService;
import com.vss.social_webapp.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/like")
public class LikeController {

    @Autowired
    LikePostService likePostService;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    //    @GetMapping("/")
//    public String getLike(){
//        return "re"
//    }
    @PostMapping
    public String doLikeOrUnlikeAction(@ModelAttribute(value = "isActive") String isActive, @ModelAttribute("userName") String userName, @ModelAttribute(value = "postCode") String postCode) {
        if (!isActive.isEmpty()) {
            User user = new User();
            if (userName.matches(CommonConst.Regex.PHONE)) {
                user = userService.findByPhoneNumber(userName);

            } else if (userName.matches(CommonConst.Regex.EMAIL)) {
                user = userService.findByUserEmail(userName).get();

            }


            Optional<Post> post = postService.findByCode(postCode);

            if (likePostService.checkIfUserLikedPost(userName, postCode)) {
                LikePost likePost = new LikePost();
                String code = RandomStringUtils.randomAlphanumeric(8);
                likePost.setCode(code);
                likePost.setUserName(user.getUserName());
                likePost.setProfilePicture(user.getProfilePicture());
                likePost.setPostCode(postCode);
                likePost.setActive(true);
                long sum = post.get().getSumLike() + 1;
                post.get().setSumLike(sum);
                likePostService.saveLikePost(likePost);
            } else {
                List<LikePost> likePosts = likePostService.findByUserNameAndPostCode(userName, postCode);
                if (!likePosts.isEmpty()) {
                    for (LikePost likePost : likePosts) {
                        if (likePost.getCommentCode() == null) {





                            likePost.setActive(!likePost.isActive());
                            post.get().setSumLike(likePost.isActive()?(post.get().getSumLike() - 1):(post.get().getSumLike() + 1));
                            likePostService.saveLikePost(likePost);
                        }
                    }
                }

            }
            postService.savePost(post.get());

        }
        return "redirect:/home";
    }

    @PostMapping("/comment")
    public String getLikeComment(@ModelAttribute(value = "isActive") String isActive, @ModelAttribute(value = "userName") String userName, @ModelAttribute(value = "postCode") String postCode, @ModelAttribute(name = "commentCode") String commentCode) {
        if (!isActive.isEmpty()) {
            User user = new User();
            if (userName.matches(CommonConst.Regex.PHONE)) {
                user = userService.findByPhoneNumber(userName);

            } else if (userName.matches(CommonConst.Regex.EMAIL)) {
                user = userService.findByUserEmail(userName).get();

            }
            Optional<CommentPost> commentPost = commentService.findByCommentCode(commentCode);

            if (likePostService.checkLikeComment(userName, postCode, commentCode)) {
                LikePost likePost = new LikePost();
                String code = RandomStringUtils.randomAlphanumeric(8);
                likePost.setCode(code);
                likePost.setUserName(user.getUserName());
                likePost.setProfilePicture(user.getProfilePicture());
                likePost.setPostCode(postCode);
                likePost.setActive(true);
                likePost.setCommentCode(commentCode);
                int sum = commentPost.get().getSumLikeComment() + 1;
                commentPost.get().setSumLikeComment(sum);
//                long sum = post.get().getSumLike()+1;
//                post.get().setSumLike(sum);
                likePostService.saveLikePost(likePost);
            } else {
                Optional<LikePost> likePost = likePostService.findByUserNameAndPostCodeAndCommentCode(userName, postCode, commentCode);
                if (likePost.isPresent()) {
                    boolean check = likePost.get().isActive();
                    if (check) {
                        likePost.get().setActive(false);
                        commentPost.get().setSumLikeComment(commentPost.get().getSumLikeComment() - 1);
                        likePostService.saveLikePost(likePost.get());
                    } else {
                        likePost.get().setActive(true);
                        commentPost.get().setSumLikeComment(commentPost.get().getSumLikeComment() + 1);
                        likePostService.saveLikePost(likePost.get());
                    }
                }
            }
            commentService.saveComment(commentPost.get());

        }
        return "redirect:/comment/getComment";
    }
}
