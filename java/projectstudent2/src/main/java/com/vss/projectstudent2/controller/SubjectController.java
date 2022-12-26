package com.vss.projectstudent2.controller;

import com.vss.projectstudent2.common.CommonConst;
import com.vss.projectstudent2.model.Course;
import com.vss.projectstudent2.model.Subject;
import com.vss.projectstudent2.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;
    @GetMapping("/")
    public String subject(ModelMap modelMap){
        modelMap.addAttribute("listSubject", subjectService.getSubjectByClass());
        return CommonConst.ViewName.SUBJECT_PAGE;
    }



    @PostMapping ("/find")
    public String find(@RequestParam(value = "keyword") String keyword, ModelMap modelMap){
        List<Subject> subjects = subjectService.findSubjectAndClass(keyword);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("findList",subjects);
        modelMap.addAttribute("listSubject", subjectService.getSubjectByClass());
        return CommonConst.ViewName.SUBJECT_PAGE;

    }

    @GetMapping("/add")
    public String add(ModelMap modelMap){
        modelMap.addAttribute("subject", new Subject());
        modelMap.addAttribute("list", subjectService.getSubjectByClass());
        return CommonConst.ViewName.INSERT_SUBJECT;
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute(value = "subject") Subject subject,ModelMap modelMap){
        subjectService.insertSubject(subject);

        return CommonConst.URLName.SUBJECT;
    }

}
