package com.vss.social_webapp.controller;

import com.vss.social_webapp.dtos.FriendDTO;
import com.vss.social_webapp.model.LikePost;
import com.vss.social_webapp.model.Post;
import com.vss.social_webapp.model.RelationshipFb;
import com.vss.social_webapp.model.User;
import com.vss.social_webapp.service.LikePostService;
import com.vss.social_webapp.service.PostService;
import com.vss.social_webapp.service.RelationshipService;
import com.vss.social_webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Controller
public class ProjectController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    LikePostService likePostService;

    @Autowired
    RelationshipService relationshipService;

    @GetMapping
    public String test(){
        return "index";
    }

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
            List<RelationshipFb> relationshipFbList = relationshipService.findByPersonOneEmail(user.getEmail());
            FriendDTO friendDTO = new FriendDTO();
            List<FriendDTO> friendDTOS =  new ArrayList<>();
            for(RelationshipFb relationshipFb: relationshipFbList){
                friendDTO.setUserEmail(relationshipFb.getPersonTwoEmail());
                friendDTO.setActive(relationshipFb.isActive());
                friendDTOS.add(friendDTO);
            }
            List<RelationshipFb> relationshipFbList1 = relationshipService.findByPersonTwoEmail(user.getEmail());
            for(RelationshipFb relationshipFb: relationshipFbList1){
                friendDTO.setUserEmail(relationshipFb.getPersonOneEmail());
                friendDTO.setActive(relationshipFb.isActive());
                friendDTOS.add(friendDTO);
            }
            modelMap.addAttribute("friends", friendDTOS);
        }
        List<Post> posts = postService.getAllPost();
        modelMap.addAttribute("posts", posts);
        List<User> users = userService.getAllUser();
        modelMap.addAttribute("users", users);

        return "home";
    }
    private int sum(List<Post> posts){

        int sum = 0;
        List<LikePost> likePosts = new ArrayList<>();
        for(Post post:posts){
            likePosts = post.getLikePosts();
        }

        if(!likePosts.isEmpty()){
            for(LikePost likePost:likePosts){
                sum++;
            }
        }
        return sum;
    }
}
