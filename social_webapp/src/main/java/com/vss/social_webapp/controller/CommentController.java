package com.vss.social_webapp.controller;

import com.vss.social_webapp.common.CommonConst;
import com.vss.social_webapp.dtos.FriendDTO;
import com.vss.social_webapp.file.FileUploadUtil;
import com.vss.social_webapp.model.CommentPost;
import com.vss.social_webapp.model.Post;
import com.vss.social_webapp.model.RelationshipFb;
import com.vss.social_webapp.model.User;
import com.vss.social_webapp.service.CommentService;
import com.vss.social_webapp.service.PostService;
import com.vss.social_webapp.service.RelationshipService;
import com.vss.social_webapp.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    PostService postService;

    @Autowired
    RelationshipService relationshipService;

    @GetMapping("/getComment")
    public String getComment(ModelMap modelMap) {
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        modelMap.addAttribute("authorities", authorities);
        for (GrantedAuthority au : authorities) {
            roles.add(au.getAuthority());
        }
        modelMap.addAttribute("roles", roles);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            modelMap.addAttribute("getName", currentUserName);
            User user = userService.findByUsername(currentUserName);
            modelMap.addAttribute("user", user);
            List<RelationshipFb> relationshipFbList = relationshipService.findByPersonOneEmail(user.getEmail());
            FriendDTO friendDTO = new FriendDTO();
            List<FriendDTO> friendDTOS = new ArrayList<>();
            for (RelationshipFb relationshipFb : relationshipFbList) {
                friendDTO.setUserEmail(relationshipFb.getPersonTwoEmail());
                friendDTO.setActive(relationshipFb.isActive());
                friendDTOS.add(friendDTO);
            }
            List<RelationshipFb> relationshipFbList1 = relationshipService.findByPersonTwoEmail(user.getEmail());
            for (RelationshipFb relationshipFb : relationshipFbList1) {
                friendDTO.setUserEmail(relationshipFb.getPersonOneEmail());
                friendDTO.setActive(relationshipFb.isActive());
                friendDTOS.add(friendDTO);
            }
            modelMap.addAttribute("friends", friendDTOS);
        }

//        List<CommentPost> commentPosts = commentService.getAllCommentByPostCode();

        List<Post> posts = postService.getAllPostAndComment();
        modelMap.addAttribute("posts", posts);
        List<User> users = userService.getAllUser();
        modelMap.addAttribute("users", users);

        return "home";
    }

    @PostMapping("/create-a-comment")
    public String createComment(ModelMap modelMap, @ModelAttribute(name = "userName") String userName, @ModelAttribute(name = "postCode") String postCode, @ModelAttribute(name = "content") String contentComment, @ModelAttribute(name = "file") MultipartFile file) throws IOException {
        CommentPost commentPost = new CommentPost();
        commentPost.setContent(contentComment);
        String code = RandomStringUtils.randomAlphanumeric(8);
        commentPost.setCode(code);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        commentPost.setCreateAt(timestamp);
        User user = new User();
        if (userName.matches(CommonConst.Regex.PHONE)) {
            user = userService.findByPhoneNumber(userName);
        } else if (userName.matches(CommonConst.Regex.EMAIL)) {
            user = userService.findByUserEmail(userName).get();

        }
        commentPost.setCreateBy(user.getName());
        commentPost.setUserEmail(user.getName());
        commentPost.setPostCode(postCode);
        commentPost.setPicture(user.getProfilePicture());

        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileUploadUtil.saveFile(fileName, file);
            commentPost.setPictureComment("/myfiles/" + fileName);
        }


        commentService.saveComment(commentPost);
        return "redirect:/comment/getComment";
    }

    @PostMapping("/create-a-comment-child")
    public String createCommentChild(@ModelAttribute(name = "userName") String userName, @ModelAttribute(name = "postCode") String postCode, @ModelAttribute(name = "parentCommentCode") String parentCommentCode, @ModelAttribute(name = "content") String content, @ModelAttribute(name = "file") MultipartFile file) throws IOException {

        Optional<CommentPost> commentPost1 = commentService.findByCommentCode(parentCommentCode);

        List<CommentPost> commentPosts = commentPost1.get().getCommentPosts();


        CommentPost commentPost = new CommentPost();
        commentPost.setContent(content);
        String code = RandomStringUtils.randomAlphanumeric(8);
        commentPost.setCode(code);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        commentPost.setCreateAt(timestamp);
        User user = new User();
        if (userName.matches(CommonConst.Regex.PHONE)) {
            user = userService.findByPhoneNumber(userName);
        } else if (userName.matches(CommonConst.Regex.EMAIL)) {
            user = userService.findByUserEmail(userName).get();

        }

        commentPost.setCreateBy(user.getName());
        commentPost.setUserEmail(user.getName());
        commentPost.setPostCode(postCode);
        commentPost.setPicture(user.getProfilePicture());

        if (!file.isEmpty() && file != null) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            FileUploadUtil.saveFile(fileName, file);
            commentPost.setPictureComment("/myfiles/" + fileName);
        }

        commentPost.setParentCommentCode(parentCommentCode);

        commentService.saveComment(commentPost);

        commentPosts.add(commentPost);
        commentPost1.get().setCommentPosts(commentPosts);
        commentService.saveComment(commentPost1.get());

        return "redirect:/comment/getComment";

    }

    @GetMapping("/create-a-comment-user/{userName}/{postCode}")
    public String createCommentFromUser(ModelMap modelMap, @PathVariable(name = "userName") String userName, @PathVariable(name = "postCode") String postCode) {
        modelMap.addAttribute("comment", new CommentPost());
        modelMap.addAttribute("userName", userName);
        modelMap.addAttribute("postCode", postCode);
        return "add_comment-user-page";
    }

    @GetMapping("/create-a-comment-user")
    public String createCommentFromUser() {
        return "redirect:/user/home";
    }

    @PostMapping("/create-a-comment-user")
    public void createComment(@ModelAttribute(value = "comment") CommentPost commentPost, @ModelAttribute(value = "userName") String userName, @ModelAttribute(value = "postCode") String postCode) {
        CommentPost commentPost1 = new CommentPost();
        commentPost1.setContent(commentPost.getContent());
        String code = RandomStringUtils.randomAlphanumeric(8);
        commentPost1.setCode(code);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        commentPost1.setCreateAt(timestamp);
        User user = new User();
        if (userName.matches(CommonConst.Regex.PHONE)) {
            user = userService.findByPhoneNumber(userName);

        } else if (userName.matches(CommonConst.Regex.EMAIL)) {
            user = userService.findByUserEmail(userName).get();

        }
        commentPost1.setCreateBy(user.getName());
        commentPost1.setUserEmail(user.getName());
        commentPost1.setPostCode(postCode);
        commentPost1.setPicture(user.getProfilePicture());
        commentService.saveComment(commentPost1);
    }

    @GetMapping("/create-a-comment-group/{userName}/{postCode}")
    public String commentFromGroup(ModelMap modelMap, @PathVariable(name = "userName") String userName, @PathVariable(name = "postCode") String postCode) {
        modelMap.addAttribute("comment", new CommentPost());
        modelMap.addAttribute("userName", userName);
        modelMap.addAttribute("postCode", postCode);
        return "add_comment-group";
    }

    @GetMapping("/create-a-comment-group")
    public String commentFromGroup() {
        return "redirect:/group/home";
    }

    @PostMapping("/create-a-comment-group")
    public void createComment3(@ModelAttribute(value = "comment") CommentPost commentPost, @ModelAttribute(value = "userName") String userName, @ModelAttribute(value = "postCode") String postCode) {
        CommentPost commentPost1 = new CommentPost();
        commentPost1.setContent(commentPost.getContent());
        String code = RandomStringUtils.randomAlphanumeric(8);
        commentPost1.setCode(code);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        commentPost1.setCreateAt(timestamp);
        User user = new User();
        if (userName.matches(CommonConst.Regex.PHONE)) {
            user = userService.findByPhoneNumber(userName);

        } else if (userName.matches(CommonConst.Regex.EMAIL)) {
            user = userService.findByUserEmail(userName).get();

        }
        commentPost1.setCreateBy(user.getName());
        commentPost1.setUserEmail(user.getName());
        commentPost1.setPostCode(postCode);
        commentPost1.setPicture(user.getProfilePicture());
        commentService.saveComment(commentPost1);

    }


}
