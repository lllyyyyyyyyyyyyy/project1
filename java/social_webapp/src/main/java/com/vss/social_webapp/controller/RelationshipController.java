package com.vss.social_webapp.controller;

import com.vss.social_webapp.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class RelationshipController {

    @Autowired
    RelationshipService relationshipService;


    @GetMapping("/add-friend")
    public String addFriend(){
        return "redirect:/home";
//        relationshipService.addFriend(userEmail1, userEmail2);
    }

    @PostMapping("/add-friend")
    public String addFriend(@ModelAttribute(name = "userEmail1") String userEmail1, @ModelAttribute(value = "userEmail2") String userEmail2){
        relationshipService.addFriend(userEmail1, userEmail2);
        return "redirect:/home";
    }


}
