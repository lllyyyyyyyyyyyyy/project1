package com.vss.projectstudent2.controller;

import com.vss.projectstudent2.common.CommonConst;
import com.vss.projectstudent2.model.Classroom;
import com.vss.projectstudent2.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    ClassroomService classroomService;
    @GetMapping("/")
    public String classroom(ModelMap modelMap){
        List<Classroom> classrooms = classroomService.getAllClassroomByCourse();
        modelMap.addAttribute("listClassroom", classrooms);
        return CommonConst.ViewName.CLASSROOM_PAGE;
    }

}
