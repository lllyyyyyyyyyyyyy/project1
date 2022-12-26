package com.vss.projectstudent2.controller;

import com.vss.projectstudent2.common.CommonConst;
import com.vss.projectstudent2.model.Course;
import com.vss.projectstudent2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    CourseService courseService;
    @GetMapping("/")
    public String course(ModelMap modelMap){

        List<Course> courses = new ArrayList<>();
        courses = courseService.courseList();
        modelMap.addAttribute("courseList", courses);
        return CommonConst.ViewName.COURSE_PAGE;
    }

    @GetMapping("/add")
    public String add(ModelMap modelMap){
        modelMap.addAttribute("course", new Course());
        return CommonConst.ViewName.INSERT_COURSE;
    }

    @PostMapping("/insert-course")
    public String insert(@ModelAttribute(value = "course") Course course){
        courseService.insertCourse(course);
        return CommonConst.URLName.COURSES;
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, ModelMap modelMap){
        Course course = courseService.updateCourse(id);
        modelMap.addAttribute("course", course);
        return CommonConst.ViewName.UPDATE_COURSE;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, ModelMap modelMap){
        courseService.deleteCourse(id);
        return CommonConst.URLName.COURSES;
    }

    @PostMapping ("/find")
    public String find(@RequestParam(value = "keyword") String keyword, ModelMap modelMap){
        List<Course> courses = courseService.findCourse(keyword);

        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("findList", courses);

        modelMap.addAttribute("courseList", courseService.courseList());
        return CommonConst.ViewName.COURSE_PAGE;

    }

}
