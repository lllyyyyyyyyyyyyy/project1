package com.vss.lynt.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.vss.lynt.common.CommonConst;
import com.vss.lynt.dtos.UserDTO;
import com.vss.lynt.dtos.UserRoleDTO;
import com.vss.lynt.model.*;
import com.vss.lynt.service.RoleService;
import com.vss.lynt.service.UserService;
import com.vss.lynt.service.impliment.LoginRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    RoleService roleService;

    @Autowired
    LoginRequestServiceImpl loginRequestService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home(ModelMap modelMap){

//        modelMap.addAttribute("getAllUserAndRole", roleService.getAllUserRoleByUserIdAndRoleId());
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        modelMap.addAttribute("authorities", authorities);
        for (GrantedAuthority au : authorities) {
            roles.add(au.getAuthority());
        }
        modelMap.addAttribute("roles", roles);
        return  CommonConst.ViewName.INDEX;
    }


    @GetMapping("/test")
    public String test(){
        return "test";
    }

    //ADMIN
    //UserId: 1
    //Password: 1



    @GetMapping("/login")
    public String showLogin(ModelMap modelMap) throws NoSuchAlgorithmException {
        LoginRequest loginRequest = new LoginRequest();
        modelMap.addAttribute("loginRequest", loginRequest);
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        modelMap.addAttribute("authorities", authorities);
        for (GrantedAuthority au : authorities) {
            roles.add(au.getAuthority());
        }
        modelMap.addAttribute("roles", roles);
        return "login";
    }
    @PostMapping("/checklogin")
    public String checkLogin(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password, ModelMap modelMap) throws NoSuchAlgorithmException {

        if(loginRequestService.ckeckLogin(userName,password)){
            boolean loginSuccess = true;
            modelMap.addAttribute("loginSuccess", loginSuccess);
            return "redirect:/users/crud-user";
        }
        else{
            modelMap.addAttribute("error", "tai khoan hoac mat khau khong dung!");
            return CommonConst.UrlName.LOGIN;
        }
    }

}
