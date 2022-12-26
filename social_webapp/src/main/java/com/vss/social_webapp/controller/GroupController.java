package com.vss.social_webapp.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.vss.social_webapp.common.CommonConst;
import com.vss.social_webapp.model.*;
import com.vss.social_webapp.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequestMapping("/group")
@Controller
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    PostService postService;

    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/home")
    public String home(ModelMap modelMap) {
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
            Optional<UserGroup> userGroup = userGroupService.findByUserEmail(user.getEmail());
            modelMap.addAttribute("userGroup",userGroup.get());
        }

        List<Group> groups = groupService.getInforOfUsersGroup();
        modelMap.addAttribute("groups", groups);


        return "group_page";
    }

    @GetMapping("/create/{userName}")
    public String createGroup(@PathVariable(value = "userName") String userName, ModelMap modelMap) {
        modelMap.addAttribute("userName", userName);
        modelMap.addAttribute("group", new Group());
        return "create_group";
    }
    @GetMapping("/create")
    public String createGroup(){
        return "redirect:/group/home";
    }

    @PostMapping("/create")
    public String createGroup(@ModelAttribute(value = "group") Group group, @ModelAttribute(value = "userName") String userName) {
        String code = RandomStringUtils.randomAlphanumeric(8);
        group.setCode(code);
        groupService.saveGroup(group);
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupCode(code);
        User user = new User();
        String test = userName;
        //check null

        if (userName.matches(CommonConst.Regex.PHONE)) {
            user = userService.findByPhoneNumber(userName);
        } else if (userName.matches(CommonConst.Regex.EMAIL)) {
            user = userService.findByUserEmail(userName).get();
        }
        userGroup.setUserEmail(user.getEmail());
        userGroup.setRoleCode("adminGroup");
        userGroupService.saveUserGroup(userGroup);

        UserRole userRole = new UserRole();
        userRole.setRoleCode("adminGroup");
        userRole.setUserEmail(user.getEmail());
        userRoleService.saveUserRole(userRole);
        return "redirect:/group/home";
    }

    @GetMapping("/add-member")
    public String addMember(){
        return "redirect:/group/home";
    }
    @GetMapping("/add-member/{userName}")
    public String addMember(@PathVariable(value = "userName") String userName, ModelMap modelMap) {

        User user = new User();
        if (userName.matches(CommonConst.Regex.PHONE)) {
            user = userService.findByPhoneNumber(userName);
        } else if (userName.matches(CommonConst.Regex.EMAIL)) {
            user = userService.findByUserEmail(userName).get();
        }
        Optional<UserGroup> userGroup = userGroupService.findByUserEmail(user.getEmail());
        if (userGroup.isPresent() && userGroup.get().getRoleCode().equals("adminGroup")) {
            modelMap.addAttribute("addUser", new UserGroup());
            return "add-member-of-group";
        } else {
            modelMap.addAttribute("errorRole", "role user-group dont add member!");
            return "redirect:/group/home";
        }
    }

    @PostMapping("/add-member")
    public String addMember(@ModelAttribute(value = "addUser") UserGroup userGroup, @ModelAttribute(value = "userName") String userName, ModelMap modelMap) {

        Optional<User> user2 = userService.findByUserEmail(userGroup.getUserEmail());
        if (user2.isPresent()) {

            User user = new User();
            if (userName.matches(CommonConst.Regex.PHONE)) {
                user = userService.findByPhoneNumber(userName);
            } else if (userName.matches(CommonConst.Regex.EMAIL)) {
                user = userService.findByUserEmail(userName).get();
            }
            Optional<UserGroup> userGroup1 = userGroupService.findByUserEmail(user.getEmail());
            userGroup.setRoleCode("userGroup");
            userGroup.setGroupCode(userGroup1.get().getGroupCode());
//            userGroup.setUserEmail(userGroup.getUserEmail());
            userGroupService.saveUserGroup(userGroup);
            UserRole userRole = new UserRole();
            userRole.setRoleCode("userGroup");
            userRole.setUserEmail(user.getEmail());
            userRoleService.saveUserRole(userRole);
            return "redirect:/group/home";
        } else {
            modelMap.addAttribute("mess", "User Email is not valid. Please try again!");
            modelMap.addAttribute("userName", userName);
            return "redirect:/group/add-member{userName}";
        }
    }

    @GetMapping("/join-group")
    public String joinGroup(){
        return "redirect:/group/home";
    }
    @GetMapping("/join-group/{userName}/{groupCode}")
    public String joinGroup(@PathVariable(value = "userName") String userName, @PathVariable(value = "groupCode") String groupCode, ModelMap modelMap) {
        User user = new User();
        if (userName.matches(CommonConst.Regex.PHONE)) {
            user = userService.findByPhoneNumber(userName);
        } else if (userName.matches(CommonConst.Regex.EMAIL)) {
            user = userService.findByUserEmail(userName).get();
        }
        Optional<UserGroup> userGroup = userGroupService.findByUserEmail(user.getEmail());
        if(userGroup.isPresent()){
            modelMap.addAttribute("errorJoin", "join group roi! Join nua lam gi");
            return "redirect:/group/home";
        }
        else {
            UserGroup userGroup1 = new UserGroup();
            userGroup1.setGroupCode(groupCode);
            userGroup1.setUserEmail(user.getEmail());
            userGroup1.setRoleCode("userGroup");
            userGroupService.saveUserGroup(userGroup1);
            UserRole userRole = new UserRole();
            userRole.setRoleCode("userGroup");
            userRole.setUserEmail(user.getEmail());
            userRoleService.saveUserRole(userRole);
            return "redirect:/group/home";
        }
    }
    @GetMapping("/members-of-group")
    public String memberOfGroup(){
        return "redirect:/group/home";
    }
    @GetMapping("/members-of-group/{groupCode}")
    public String memberOfGroup(@PathVariable(value = "groupCode")String groupCode, ModelMap modelMap){
        List<UserGroup> userGroups = userGroupService.findByGroupCode(groupCode);
        List<User> users = new ArrayList<>();
        User user = new User();
        for(UserGroup userGroup:userGroups){
            user = userService.findByUserEmail(userGroup.getUserEmail()).get();
            users.add(user);
        }
        modelMap.addAttribute("listOfUserOnGroup", users);
        modelMap.addAttribute("ListOfUserGroup", userGroups);
        return "member_group";
    }

    @GetMapping("/get-group-by-groupCode")
    public String getGroup(){
        return "redirect:/group/home";
    }
    @GetMapping("/get-group-by-groupCode/{groupCode}")
    public String getGroup(@PathVariable(value = "groupCode") String groupCode, ModelMap modelMap){
        Group group = groupService.getInforByCode(groupCode);
        modelMap.addAttribute("group", group);
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

        List<Group> groups = groupService.getInforOfUsersGroup();
        modelMap.addAttribute("groups", groups);

        List<Post> posts = postService.getAllPostByGroup(groupCode);
        modelMap.addAttribute("posts", posts);

        return "group_page";
    }
    @GetMapping("/update-post")
    public String updatePost(){
        return "redirect:/group/home";
    }
    @GetMapping("/update-post/{code}")
    public String updatePost(@PathVariable(value = "code") String code, ModelMap modelMap){

        Post post = postService.findByCode(code).get();
        modelMap.addAttribute("post", post);
        return "update_post_group";
    }

    @PostMapping("/update-post")
    public String updatePost(@ModelAttribute(value = "post") Post post){
        postService.updatePost(post);
        return "redirect:/group/home";
    }
}
