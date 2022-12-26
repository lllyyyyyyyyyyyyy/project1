package com.vss.projectstudent2.controller;

import com.vss.projectstudent2.common.CommonConst;
import com.vss.projectstudent2.model.LoginRequest;
import com.vss.projectstudent2.model.User;
import com.vss.projectstudent2.service.LoginRequestService;
import com.vss.projectstudent2.service.UserService;
import lombok.extern.java.Log;
import org.apache.commons.codec.cli.Digest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {
//    @Autowired
//    LoginRequest loginRequest;

    @Autowired
    LoginRequestService loginRequestService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home(ModelMap modelMap){

        return CommonConst.ViewName.HOME;
    }
    @GetMapping("/login")
    public String showLogin(ModelMap modelMap) throws NoSuchAlgorithmException {
        LoginRequest loginRequest = new LoginRequest();
        modelMap.addAttribute("loginRequest", loginRequest);


//        messageDigest.update(loginRequest.getPassword());

        return "login";
    }



    @PostMapping("/checklogin")
    public String checkLogin(ModelMap modelMap,@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password) throws NoSuchAlgorithmException {

//        if(loginRequest.getUserName().equals(userName) && loginRequest.getPassword().equals(password)){
//            return "view-user";
//        }
//
//        else{
//            return "redirect:/login";
//        }

        List<User> userLíst = userService.getAllUser();
        modelMap.addAttribute("listUser", userLíst);

        if(loginRequestService.ckeckLogin(userName,password)){
//            List<LoginRequest> loginRequests = loginRequestService.getAll();
//            modelMap.addAttribute("password", loginRequestService.encryptPassword(password));
//            modelMap.addAttribute("getAll", loginRequests);

            return "view-user";
        }
        return "redirect:/login";
    }



//    @PostMapping("/checklogin")
//    public String handleLogin(@Valid @ModelAttribute(value = "loginRequest") LoginRequest loginRequest, BindingResult bindingResult){
//
//        if(bindingResult.hasErrors())   return "login";
//        return CommonConst.URLName.HOME;
//    }
//

}
