package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {
    @GetMapping("/test")
    public String test(){
        return "index";
    }

}
