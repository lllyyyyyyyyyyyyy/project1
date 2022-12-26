package com.vss.social_webapp.controller;

import com.vss.social_webapp.common.CommonConst;
import com.vss.social_webapp.dtos.LoginRequest;
import com.vss.social_webapp.model.User;
import com.vss.social_webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.matches;


@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String showLogin(ModelMap modelMap) throws NoSuchAlgorithmException {
        LoginRequest loginRequest = new LoginRequest();
        modelMap.addAttribute("loginRequest", loginRequest);
        return "login";
    }
    @PostMapping("/checklogin")
    public String checkLogin(@RequestParam(value = "userName") String userName, @RequestParam(value = "password") String password, ModelMap modelMap) {

//        Pattern pattern = Pattern.compile(CommonConst.Regex.PHONE);
//        Matcher matcher = pattern.matcher(userName);
//        boolean matche = matcher.matches();
        if(userName.matches(CommonConst.Regex.PHONE)){
            User user = userService.findByPhoneNumber(userName);
            user.setUserName(userName);
            userService.saveUser(user);
        } else if (userName.matches(CommonConst.Regex.EMAIL)) {
            User user = userService.findByUserEmail(userName).get();
            user.setUserName(userName);
            userService.saveUser(user);
        }

        if(userService.ckeckLogin(userName,password)){
            boolean loginSuccess = true;
            modelMap.addAttribute("loginSuccess", loginSuccess);
            return "redirect:/home";
        }
        else{
            modelMap.addAttribute("error", "tai khoan hoac mat khau khong dung!");
            return "redirect:/login";
        }
    }
}
