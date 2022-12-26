package com.vss.projectstudent.Controller;

import com.vss.projectstudent.Common.CommonConst;
import com.vss.projectstudent.Model.Student;
import com.vss.projectstudent.Service.ClassroomService;
import com.vss.projectstudent.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ControllerProject {

    @Autowired
    private StudentService studentService;

    @Autowired
     private ClassroomService classroomService;

    @GetMapping("/")
    public String homePage(ModelMap modelMap) {
        modelMap.addAttribute("list", studentService.getListStudent());
        modelMap.addAttribute("student", new Student());
        modelMap.addAttribute("listClass", classroomService.getListClass());
        modelMap.addAttribute("studentAndClass", studentService.getInfor());
        return CommonConst.ViewName.HOME;
    }

    @GetMapping("/addStudent")
    public String getInsertStudent(ModelMap modelMap) {
        modelMap.addAttribute("student", new Student());
        return CommonConst.ViewName.INSERT;
    }

    @PostMapping("/insert")
    public String insertStudent(@ModelAttribute("student") Student student) {
        studentService.insertStudent(student);
        return CommonConst.URLName.HOME;
    }

    @GetMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, ModelMap modelMap) {
        Student student = studentService.updateStudent(id);
        modelMap.addAttribute("student", student);
        return CommonConst.ViewName.UPDATE;
    }

    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return CommonConst.URLName.HOME;
    }


//    @PostMapping("/find")
//    public String findStudent(@ModelAttribute("student") Student student , ModelMap modelMap) {
//
//        List<Student> students = studentService.findStudent(student);
//        modelMap.addAttribute("listFind", students);
////        return CommonConst.URLName.HOME;
//        return "homepage";
//
//    }

    @PostMapping("/findbykeyword")
    public String findStudent(@RequestParam (value = "keyword") String keyword, ModelMap modelMap){
        List<Student> students = studentService.findStudent(keyword);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("findList", students);
        modelMap.addAttribute("list", studentService.getListStudent());
        modelMap.addAttribute("studentAndClass", studentService.getInfor());
        modelMap.addAttribute("listClass", classroomService.getListClass());
        return CommonConst.ViewName.HOME;
    }

    @GetMapping("/hello")
    public String wellcome(ModelMap modelMap){
        modelMap.addAttribute("list", studentService.getListStudent());
        return CommonConst.ViewName.HELLO;
    }

    @PostMapping ("/get-course")
    public String getCourses(@RequestParam (value = "course_id") String courseId, ModelMap modelMap) {
        if (courseId.equals("K62")) {
            courseId = "K62";
        }
        else if(courseId.equals("K63")){
            courseId = "K63";
        }
        else courseId = "K64";
        List<Student> students = studentService.getStudentByCourses(courseId);
        modelMap.addAttribute("list2", students);
        return CommonConst.ViewName.HELLO;
    }

//    @PostMapping("/get-courses/get-class")
//    public String getClass(@RequestParam (value = "class_id") String classId, ModelMap modelMap){
////        List<Student> students = studentService
//    }


//    @PostMapping("/get-class-k62")
//    public String getK10(@RequestParam(value = "class_id") String classId){
//        if(classId.equals("K62DA")){
//            return "K62DA";
//        }
//        else if(classId.equals("K62DB")){
//            return "K62DB";
//        }
//        else if(classId.equals("K62CA")){
//            return "K62CA";
//        }
//        else return "K62CB";
//    }
//
//    @PostMapping("/get-class-k63")
//    public String getK11(@RequestParam(value = "class_id") String classId){
//        if(classId.equals("K63DA")){
//            return "K63DA";
//        }
//        else if(classId.equals("K63DB")){
//            return "K63DB";
//        }
//        else if(classId.equals("K63CA")){
//            return "K63CA";
//        }
//        else return "K63CB";
//    }


}
