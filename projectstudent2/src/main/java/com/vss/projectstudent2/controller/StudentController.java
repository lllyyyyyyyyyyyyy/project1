package com.vss.projectstudent2.controller;


import com.vss.projectstudent2.common.CommonConst;
import com.vss.projectstudent2.model.Student;
import com.vss.projectstudent2.service.ClassroomService;
import com.vss.projectstudent2.service.StudentService;
import com.vss.projectstudent2.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/")
    public String home(ModelMap modelMap){

        modelMap.addAttribute("getStudent", studentService.getStudent());
        classroomService.getAllClassroomByCourse();
        List<Student> students = studentService.getInforStudentByClass();
        modelMap.addAttribute("listStudent", students);
        return CommonConst.ViewName.STUDENTPAGE;


//        return CommonConst.ViewName.HOME;
    }

//    @GetMapping ("/get-class")
//    public String getClass(@RequestParam("coursesId") String coursesId, ModelMap modelMap){
//
//        modelMap.addAttribute("listStudentByClass", studentService.getInforStudentByClass());
//        modelMap.addAttribute("coursesId", coursesId);
////        List<Classroom> getClassByCourse = classroomService.getAll(coursesId);
////        modelMap.addAttribute("listClass",getClassByCourse);
//        return  CommonConst.ViewName.STUDENTPAGE;
//    }

//    @GetMapping ("/get-student-by-class")
//    public String getStudent(ModelMap modelMap){
//
////        modelMap.addAttribute("classId", classId);
//
//
//
////        modelMap.addAttribute("listStudentByClass", studentService.getInforStudentByClass());
////        modelMap.addAttribute("getStudent", studentService.getStudent());
////        List<Student> getStudentByClassId = studentService.getAllStudent(classId);
////        modelMap.addAttribute("listStudentByClass", getStudentByClassId);
//        return  CommonConst.ViewName.STUDENTPAGE;
//    }



}
