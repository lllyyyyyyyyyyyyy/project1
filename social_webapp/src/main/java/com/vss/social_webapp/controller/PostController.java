package com.vss.social_webapp.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.vss.social_webapp.common.CommonConst;
import com.vss.social_webapp.dtos.FileUploadResponse;
import com.vss.social_webapp.file.FileUploadUtil;
import com.vss.social_webapp.model.*;
import com.vss.social_webapp.service.*;
import javafx.geometry.Pos;
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
@RequestMapping("/post")
public class PostController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    PictureService pictureService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    GroupService groupService;

    @GetMapping("/home")
    public String home(ModelMap modelMap){
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
        }
        return "post";
    }
    @GetMapping("/add-post-user")
    public String addPostFromUser(){
        return "redirect:/user/home";
    }
    @GetMapping("/add-post-user/{userName}")
    public String addPostFromUser(@PathVariable(name = "userName") String userName, ModelMap modelMap){

        User user = userService.findByUsername(userName);
        Post post = new Post();
        post.setUserEmail(user.getEmail());
        modelMap.addAttribute("post", post);
        return "add-post";

    }
    @PostMapping("/add-post-user")
    public String addPostFromUser(@ModelAttribute(value = "userName")String userName,@ModelAttribute(value = "post")Post post, @RequestParam(value = "files") List<MultipartFile> files) throws IOException {
        User user = new User();
        if(userName.matches(CommonConst.Regex.EMAIL)){
            user = userService.findByUserEmail(userName).get();
        } else if (userName.matches(CommonConst.Regex.PHONE)) {
            user = userService.findByPhoneNumber(userName);
        }

        Post post1 = new Post();
        post1.setContent(post.getContent());
//        post1.
        String code = RandomStringUtils.randomAlphanumeric(8);
        post1.setProfilePicture(false);
        post1.setCode(code);
        post1.setUserEmail(user.getEmail());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post1.setCreatedAt(timestamp);
        post1.setCreatedBy(user.getUserName());
        postService.savePost(post1);

        for(MultipartFile file:files){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            long size = file.getSize();

            FileUploadUtil.saveFile(fileName, file);

            FileUploadResponse response = new FileUploadResponse();
            response.setFileName(fileName);
            response.setSize(size);
            response.setDownloadUri("/myfiles/"+fileName);

            Picture picture = new Picture();
            String codePic = RandomStringUtils.randomAlphanumeric(8);
            picture.setCode(codePic);
            picture.setUrl(response.getDownloadUri());
            picture.setName(response.getFileName());
            picture.setPostCode(post1.getCode());
            picture.setUserEmail(user.getEmail());

            Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
            picture.setCreatedAt(timestamp2);
            picture.setCreatedBy(user.getUserName());
            pictureService.savePicture(picture);
        }

        return "redirect:/user/home";
    }

//    @GetMapping("/update-post-user-{userName}")
//    public String updatePostFromUser(ModelMap modelMap, @PathVariable(value = "userName")String userName){
//
//    }


    @GetMapping("/add-post-group")
    public String addPostFromGroup(){
        return "redirect:/group/home";
    }
    @GetMapping("/add-post-group/{userName}/{groupCode}")
    public String addPostFromGroup(@PathVariable(value = "userName") String userName, @PathVariable(value = "groupCode") String groupCode, ModelMap modelMap){
        Group group = groupService.findByCode(groupCode);
        Post post = new Post();

        User user = new User();
        if(userName.matches(CommonConst.Regex.EMAIL)){
            user = userService.findByUserEmail(userName).get();
        } else if (userName.matches(CommonConst.Regex.PHONE)) {
            user = userService.findByPhoneNumber(userName);
        }

        post.setUserEmail(user.getEmail());
        post.setUserGroupCode(group.getCode());
        modelMap.addAttribute("post",post);
        return "add_post_of_group";
    }
    @PostMapping("/add-post-group")
    public String addPostFromGroup(@ModelAttribute(value = "post") Post post, ModelMap modelMap, List<MultipartFile> files) throws IOException {

        Post post1 = new Post();
        post1.setContent(post.getContent());

        String code = RandomStringUtils.randomAlphanumeric(8);
        post1.setCode(code);
        post1.setProfilePicture(false);
        post1.setUserEmail(post.getUserEmail());
        post1.setUserGroupCode(post.getUserGroupCode());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post1.setCreatedAt(timestamp);
        post1.setCreatedBy(post.getUserEmail());
        postService.savePost(post1);

        for(MultipartFile file:files){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            long size = file.getSize();

            FileUploadUtil.saveFile(fileName, file);

            FileUploadResponse response = new FileUploadResponse();
            response.setFileName(fileName);
            response.setSize(size);
            response.setDownloadUri("/myfiles/"+fileName);

            Picture picture = new Picture();
            String codePic = RandomStringUtils.randomAlphanumeric(8);
            picture.setCode(codePic);
            picture.setUrl(response.getDownloadUri());
            picture.setName(response.getFileName());
            picture.setPostCode(post1.getCode());
            picture.setUserEmail(post.getUserEmail());

            Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
            picture.setCreatedAt(timestamp2);
            picture.setCreatedBy(post.getUserEmail());
            pictureService.savePicture(picture);




        }
        return "redirect:/group/home";
    }

//
//    @PostMapping("/share")
//    public String getSharePost(@ModelAttribute(name = "userName") String userName, @ModelAttribute(name = "postCode") String postCode) {
//
//        User user = new User();
//        if (userName.matches(CommonConst.Regex.EMAIL)) {
//            user = userService.findByUserEmail(userName).get();
//        } else if (userName.matches(CommonConst.Regex.PHONE)) {
//            user = userService.findByPhoneNumber(userName);
//        }
//        Post post1 = new Post();
//        String code = RandomStringUtils.randomAlphanumeric(8);
//        post1.setProfilePicture(false);
//        post1.setCode(code);
//        post1.setUserEmail(user.getEmail());
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        post1.setCreatedAt(timestamp);
//        post1.setCreatedBy(user.getUserName());
//        post1.setShareFrom(postCode);
//        postService.savePost(post1);
//        return "redirect:/home";
//    }


    @GetMapping("/share")
    public String getSharePost(){
        return "redirect:/home";
    }

}
