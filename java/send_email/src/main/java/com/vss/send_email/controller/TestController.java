package com.vss.send_email.controller;

import com.vss.send_email.model.LoginRequest;
import com.vss.send_email.model.User;
import com.vss.send_email.service.LoginRequestService;
import com.vss.send_email.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@Controller
public class TestController {

    @Autowired
    UserService userService;

    @Autowired
    LoginRequestService loginRequestService;
    @GetMapping("/")
    public String test(){
        return "index";
    }

    @GetMapping("/register")
    public String showSignUpForm(ModelMap modelMap){
        modelMap.addAttribute("user", new User());
        return "signup-form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        userService.register(user, getSiteURL(request));
        return "register_success";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("/login")
    public String showLogin(ModelMap modelMap) throws NoSuchAlgorithmException {
        LoginRequest loginRequest = new LoginRequest();
        modelMap.addAttribute("loginRequest", loginRequest);
        return "login";
    }
    @PostMapping("/checklogin")
    public String checkLogin(ModelMap modelMap, @RequestParam(value = "email") String email, @RequestParam(value = "password") String password) throws NoSuchAlgorithmException {

        if(loginRequestService.ckeckLogin(email,password)){
            return "login_success";
        }
        else return "redirect:/login";
    }
    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
}
