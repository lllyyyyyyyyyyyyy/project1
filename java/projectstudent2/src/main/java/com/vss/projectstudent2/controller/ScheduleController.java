package com.vss.projectstudent2.controller;

import com.vss.projectstudent2.common.CommonConst;
import com.vss.projectstudent2.model.Classroom;
import com.vss.projectstudent2.model.Student;
import com.vss.projectstudent2.service.ClassroomService;
import com.vss.projectstudent2.service.StudentService;
import com.vss.projectstudent2.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    StudentService studentService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    ClassroomService classroomService;

    @GetMapping("/")
    public String schedule(ModelMap modelMap) {


        modelMap.addAttribute("getStudent", studentService.getStudent());

//        subjectService.getSubjectByClass();
        modelMap.addAttribute("listClassAndSubject", subjectService.getSubjectByClass());
        classroomService.getAllClassroomByCourse();
        List<Student> students = studentService.getInforStudentByClass();
        modelMap.addAttribute("listStudentByClass", students);

        return CommonConst.ViewName.SCHEDULE_PAGE;
    }

    @GetMapping("/getclass")
    public String getClass(@RequestParam(value = "courseCode") String courseCode, ModelMap modelMap) {
        List<Classroom> rooms = classroomService.findByCourseCode(courseCode);
        modelMap.addAttribute("listClassroom", rooms);
//        modelMap.addAttribute("listClassAndSubject", subjectService.getSubjectByClass());
//        modelMap.addAttribute("listStudentByClass", studentService.getInforStudentByClass());
        return CommonConst.ViewName.SCHEDULE_PAGE;
    }

}
