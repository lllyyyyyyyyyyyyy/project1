package com.vss.securityrolebaseauthorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {
    @GetMapping
    public String test(){
        return "test";
    }
}
