package com.vss.projectstudent2.controller;


import com.vss.projectstudent2.common.CommonConst;
import com.vss.projectstudent2.model.Course;
import com.vss.projectstudent2.model.User;
import com.vss.projectstudent2.model.UserDTO;
import com.vss.projectstudent2.model.UserUpdatePassword;
import com.vss.projectstudent2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller

@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/home-user")
    public String homeUser(ModelMap modelMap){
        List<User> userLíst = userService.getAllUser();
        modelMap.addAttribute("listUser", userLíst);
        modelMap.addAttribute("newUser", new User());
        modelMap.addAttribute("user", new UserDTO());
        return "view-user";
    }
    @GetMapping("/addUser")
    public String addUser(ModelMap modelMap){
        modelMap.addAttribute("newUser", new User());
        modelMap.addAttribute("user", new UserDTO());
        return "insertUser";
    }
    @PostMapping("/insert-user")
    public String insert(@ModelAttribute(value = "user") UserDTO userDTO){
        if(userService.checkInsert(userDTO.getUserName(), userDTO.getPassword()))
        {
            return "redirect:/user/addUser";
        }
        else {
            userService.insertUser(userDTO);
            return "redirect:/user/home-user";
        }
    }

    @GetMapping  ("/updateUser/{id}")
    public String update(@PathVariable Long id, ModelMap modelMap){
        modelMap.addAttribute("userUpdate", userService.update(id));
        List<User> userLíst = userService.getAllUser();
        modelMap.addAttribute("listUser", userLíst);
        modelMap.addAttribute("user", new UserDTO());
        return "updateUser";
    }




}
