package com.vss.social_webapp.controller;

import com.vss.social_webapp.common.CommonConst;
import com.vss.social_webapp.dtos.FileUploadResponse;
import com.vss.social_webapp.dtos.UserDTO;
import com.vss.social_webapp.file.FileUploadUtil;
import com.vss.social_webapp.model.Picture;
import com.vss.social_webapp.model.Post;
import com.vss.social_webapp.model.User;
import com.vss.social_webapp.service.PictureService;
import com.vss.social_webapp.service.PostService;
import com.vss.social_webapp.service.UserRoleService;
import com.vss.social_webapp.service.UserService;
import com.vss.social_webapp.service.impliment.MyUserDetails;
import org.apache.commons.collections4.Get;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    PostService postService;

    @Autowired
    PictureService pictureService;

    @GetMapping("/create")
    public String createUser(ModelMap modelMap){
        modelMap.addAttribute("user", new UserDTO());
        return "signup-form";
    }
    @PostMapping("/create")
    public String createUser(@ModelAttribute(value = "user") UserDTO user,ModelMap modelMap,@ModelAttribute(value = "password") String password,@ModelAttribute(value = "re-password") String rePassword ,@ModelAttribute(value = "gender") String gender, HttpServletRequest request) throws MessagingException, com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException, UnsupportedEncodingException {
        User user1 = new User();
        if(rePassword.equals(password)){
            user1.setEmail(user.getEmail());
            user1.setPassword(bCryptPasswordEncoder.encode(password));
            user1.setPhoneNumber(user.getPhoneNumber());
            String test = user.getDateOfBirth();
            user1.setDateOfBirth(Date.valueOf(test));
            user1.setName(user.getName());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            user1.setCreatedAt(timestamp);
            user1.setCreatedBy(user.getEmail());
            if(!gender.isEmpty()){
                user1.setGender(gender);
            }
        }
        else{
            modelMap.addAttribute("mess", "Your password do not match. Please try again!");
            return "redirect:/user/create";
        }
        userService.register(user1, getSiteURL(request));
        userRoleService.addRoleUser(user1.getEmail());
        return "create_success";
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("/home")
    public String homeUser(ModelMap modelMap, HttpServletRequest request){
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        modelMap.addAttribute("authorities", authorities);
        for (GrantedAuthority au : authorities) {
            roles.add(au.getAuthority());
        }
        modelMap.addAttribute("roles",roles);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            modelMap.addAttribute("getName", currentUserName);
            User user = userService.findByUsername(currentUserName);
            modelMap.addAttribute("user", user);
            List<Post> posts = postService.getAllPostByUser(user.getEmail());
            modelMap.addAttribute("posts", posts);
        }

        return "user";
    }

    @GetMapping("/edit-profile")
    public String editProfile(){
        return "redirect:/user/home";
    }
    @GetMapping("/edit-profile/{userName}")
    public String editProfile(@PathVariable(value = "userName") String userName, ModelMap modelMap){
//        modelMap.addAttribute("getName", authentication.getName());
        if(userName.matches(CommonConst.Regex.EMAIL)){
            User user = userService.findByUserEmail(userName).get();
            modelMap.addAttribute("user", user);
        } else if (userName.matches(CommonConst.Regex.PHONE)) {
            User user = userService.findByPhoneNumber(userName);
            modelMap.addAttribute("user", user);
        }
        return "user_update";
    }
    @PostMapping("/edit-profile")
    public String editProfile(@ModelAttribute(value = "user") User user, @ModelAttribute(value = "gender")String gender){

        User user1 = userService.findByUserEmail(user.getEmail()).get();
        user1.setName(user.getName());
        user1.setPassword(user.getPassword());
        user1.setDateOfBirth(user.getDateOfBirth());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        if(!gender.isEmpty()){
            user1.setGender(gender);
        }
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user1.setUpdateAt(timestamp);
        user1.setUpdateBy(user.getEmail());
        userService.updateInfoUser(user1);
        return "redirect:/login?updatesuccess=true";
    }
    @GetMapping("/update-password")
    public String updatePassword(){
        return "redirect:/user/home";
    }
    @GetMapping("/update-password/{userName}")
    public String updatePassword(@PathVariable(name = "userName")String userName, ModelMap modelMap){
        if(userName.matches(CommonConst.Regex.EMAIL)){
            User user = userService.findByUserEmail(userName).get();
            modelMap.addAttribute("user", user);
        } else if (userName.matches(CommonConst.Regex.PHONE)) {
            User user = userService.findByPhoneNumber(userName);
            modelMap.addAttribute("user", user);
        }
        return "update-password";
    }
    @PostMapping("/update-password")
    public String updatePassword(ModelMap modelMap,@ModelAttribute(value = "old-password")String oldPassword,@ModelAttribute(name = "user") User user, @ModelAttribute(value = "re-password") String rePassword){
        User user1 = userService.findByUserEmail(user.getEmail()).get();
        if(rePassword.equals(user.getPassword()) && bCryptPasswordEncoder.matches(oldPassword, user1.getPassword())){
                user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                userService.saveUser(user1);
                return "redirect:/login?updatesuccess=true";
        }
        else {
            modelMap.addAttribute("mess", "Your password do not match or old password incorrect. Please try again!");
            return "redirect:/user/update-password{userName}";
        }
    }
    @GetMapping("/update-email")
    public String updateEmail(){
        return "redirect:/user/home";
    }
    @GetMapping("/update-email/{userName}")
    public String updateEmail(@PathVariable(value = "userName") String userName, ModelMap modelMap){
        User user = userService.findByUsername(userName);
//        user.setEmail(null);
        modelMap.addAttribute("user", user);
        return "update_email";
    }
    @PostMapping("/update-email")
    public String updateEmail(@ModelAttribute(value = "user") User user,ModelMap modelMap,HttpServletRequest request, @ModelAttribute(value = "old-email")String oldEmail) throws MessagingException, com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException, UnsupportedEncodingException {
        User user1 = userService.findByUserEmail(oldEmail).get();
        if(user1.isEnabled()){
            user1.setEmail(user.getEmail());
            userService.saveUser(user1);
            userService.register(user1, getSiteURL(request));
            return "create_success";
        }
        else {
            modelMap.addAttribute("mess", "don't find old email in db. Please try again!");
            return "redirect:/user/update-password{userName}";
        }
    }
    @GetMapping("/add-profile-picture")
    public String addProfilePicture(){
        return "redirect:/user/home";
    }
    @GetMapping("/add-profile-picture/{userName}")
    public String addProfilePicture(@PathVariable(value = "userName")String userName, ModelMap modelMap){
        if(userName.matches(CommonConst.Regex.EMAIL)){
            User user = userService.findByUserEmail(userName).get();
            modelMap.addAttribute("user", user);
        } else if (userName.matches(CommonConst.Regex.PHONE)) {
            User user = userService.findByPhoneNumber(userName);
            modelMap.addAttribute("user", user);
        }
        return "add-profile_picture";
    }
    @PostMapping("/add-profile-picture")
    public String addProfilePicture(@ModelAttribute(value = "user")User user, ModelMap modelMap, @RequestParam(value = "image") MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long size = file.getSize();

        FileUploadUtil.saveFile(fileName, file);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/myfiles/"+fileName);

        User user1 = userService.findByUserEmail(user.getEmail()).get();
        user1.setProfilePicture(response.getDownloadUri());
        userService.saveUser(user1);

        Post post1 = new Post();
        post1.setProfilePicture(true);
        String code = RandomStringUtils.randomAlphanumeric(8);
        post1.setCode(code);
        post1.setUserEmail(user1.getEmail());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post1.setCreatedAt(timestamp);
        post1.setCreatedBy(user1.getUserName());
        postService.savePost(post1);


        Picture picture = new Picture();
        String codePic = RandomStringUtils.randomAlphanumeric(8);
        picture.setCode(codePic);
        picture.setUrl(response.getDownloadUri());
        picture.setName(response.getFileName());
        picture.setPostCode(post1.getCode());
        picture.setUserEmail(user1.getEmail());

        picture.setCreatedAt(timestamp);
        picture.setCreatedBy(user.getUserName());
        pictureService.savePicture(picture);

        return "redirect:/user/home";
    }

    @GetMapping("/add-cover-picture")
    public String addCoverPicture(){
        return "redirect:/user/home";
    }
    @GetMapping("/add-cover-picture/{userName}")
    public String addCoverPicture(@PathVariable(value = "userName")String userName, ModelMap modelMap){
        if(userName.matches(CommonConst.Regex.EMAIL)){
            User user = userService.findByUserEmail(userName).get();
            modelMap.addAttribute("user", user);
        } else if (userName.matches(CommonConst.Regex.PHONE)) {
            User user = userService.findByPhoneNumber(userName);
            modelMap.addAttribute("user", user);
        }
        return "add-cover-picture";
    }
    @PostMapping("/add-cover-picture")
    public String addCoverPicture(@ModelAttribute(value = "user")User user, ModelMap modelMap, @RequestParam(value = "image") MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long size = file.getSize();

        FileUploadUtil.saveFile(fileName, file);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/myfiles/"+fileName);

        User user1 = userService.findByUserEmail(user.getEmail()).get();
        user1.setCoverPicture(response.getDownloadUri());
        userService.saveUser(user1);


        Post post1 = new Post();
        post1.setProfilePicture(true);
        String code = RandomStringUtils.randomAlphanumeric(8);
        post1.setCode(code);
        post1.setUserEmail(user1.getEmail());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post1.setCreatedAt(timestamp);
        post1.setCreatedBy(user1.getUserName());
        postService.savePost(post1);


        Picture picture = new Picture();
        String codePic = RandomStringUtils.randomAlphanumeric(8);
        picture.setCode(codePic);
        picture.setUrl(response.getDownloadUri());
        picture.setName(response.getFileName());
        picture.setPostCode(post1.getCode());
        picture.setUserEmail(user1.getEmail());

        picture.setCreatedAt(timestamp);
        picture.setCreatedBy(user.getUserName());
        pictureService.savePicture(picture);
        return "redirect:/user/home";
    }

    @GetMapping("/update-post")
    public String updatePost(){
        return "redirect:/user/home";
    }
    @GetMapping("/update-post/{code}")
    public String updatePost(@PathVariable(value = "code") String code, ModelMap modelMap){

        Post post = postService.findByCode(code).get();
        modelMap.addAttribute("post", post);
        return "update_post_user";
    }

    @PostMapping("/update-post")
    public String updatePost(@ModelAttribute(value = "post") Post post){
        postService.updatePost(post);
        return "redirect:/user/home";
    }
//    @GetMapping("")


}
