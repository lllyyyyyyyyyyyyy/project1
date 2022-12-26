package com.vss.lynt.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import com.vss.lynt.file.FileUploadUtil;
import com.vss.lynt.file.ImageUploadUtil;
import com.vss.lynt.model.FileUploadResponse;
import com.vss.lynt.model.Student;
import com.vss.lynt.model.Subject;
import com.vss.lynt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping
    public String home(ModelMap modelMap){
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        modelMap.addAttribute("authorities", authorities);
        for (GrantedAuthority au : authorities) {
            roles.add(au.getAuthority());
        }
        modelMap.addAttribute("roles", roles);
        modelMap.addAttribute("getAllStudent", studentService.getAll());
        return "crud_student";
    }
    @GetMapping("/update-{id}")
    public String updateInformation(@PathVariable(value = "id") Integer id, ModelMap modelMap){

        Student student = studentService.findbyId(id);
        modelMap.addAttribute("student", student);
        return "update-student";
    }
    @PostMapping("/update")
    public String updateImformation(@ModelAttribute(value = "student") Student student, @RequestParam(value = "image")MultipartFile file, ModelMap modelMap) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long size = file.getSize();

        FileUploadUtil.saveFile(fileName, file);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/myfiles/"+fileName);

        Student student1 = studentService.findbyId(student.getId());
        student1.setAvatar(response.getDownloadUri());
        student1.setNameImage(response.getFileName());
        studentService.insertStudent(student1);
        return "redirect:/student";
    }
    @GetMapping("/delete-image/{id}")
    public String deleteImg(@PathVariable(value = "id") Integer id, ModelMap modelMap){
        Student student = studentService.findbyId(id);
        String url = "D:/lynt1/project/java/lynt/Files-Upload/"+ student.getNameImage();
        try{
            boolean isDelete =  Files.deleteIfExists(Paths.get(url));
            if(isDelete){
                modelMap.addAttribute("messenger_delete", "file delete successfully!");
                student.setAvatar(null);
            }
            else{
                modelMap.addAttribute("messenger_delete", "file doesn't exist!");
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }

        return "redirect:/student";
    }

    @GetMapping("/update-infor-{id}")
    public String updateInfor(ModelMap modelMap, @PathVariable(value = "id") Integer id){
        Student student = studentService.findbyId(id);
        modelMap.addAttribute("student", student);
        return "student_update_infor";
    }
    @PostMapping("/update-infor")
    public String updateInfor(@ModelAttribute(value = "student") Student student){
        Student student1 = studentService.findbyStudentId(student.getStudentId()).get();
        student1.setUserName(student.getUserName());
        student1.setDateOfBirth(student.getDateOfBirth());
        student1.setAddress(student.getAddress());
        student1.setEmail(student.getEmail());
        student1.setStudentId(student.getStudentId());
        student1.setFullName(student.getFullName());
        student1.setPassword(bCryptPasswordEncoder.encode(student.getPassword()));
        studentService.insertStudent(student1);
        return "redirect:/student";
    }
}
