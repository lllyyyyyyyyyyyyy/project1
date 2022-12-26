package com.vss.lynt.controller;

import com.vss.lynt.dtos.SubjectDTO;
import com.vss.lynt.file.FileDownloadUtil;
import com.vss.lynt.file.FileUploadUtil;
import com.vss.lynt.model.ClassSubject;
import com.vss.lynt.model.FileUploadResponse;
import com.vss.lynt.model.Subject;
import com.vss.lynt.service.ClassSubjectService;
import com.vss.lynt.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;

@Controller
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @Autowired
    ClassSubjectService classSubjectService;

    @GetMapping
    public String home(ModelMap modelMap, HttpServletResponse response) throws Exception {
        List<String> roles = new ArrayList<>();
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//        modelMap.addAttribute("authorities", authorities);
        for (GrantedAuthority au : authorities) {
            roles.add(au.getAuthority());
        }
        modelMap.addAttribute("roles", roles);
        modelMap.addAttribute("subjectList", subjectService.getAllSubject());

//        modelMap.addAttribute("cla")
//        byte[] bytes = ("<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n    <style>\n        li a {\n            display: block;\n            color: white;\n            text-align: center;\n            padding: 16px;\n            text-decoration: none;\n        }\n\n        li {\n            float: left;\n        }\n        li a:hover {\n            background-color: #111111;\n        }\n\n        table{\n            background-color: antiquewhite;\n            text-align: center;\n            font-size: 26px;\n            padding: 10px;\n        }\n\n        body {\n            background-color: dimgrey;\n        }\n        button{\n            display: block;\n            padding: 10px;\n            background-color: cornflowerblue;\n            color: #555;\n            text-align: center;\n            font-size: 16px;\n            text-decoration: none;\n        }\n    </style>\n</head>\n<body>\n\n<ul style=\"list-style-type: none;\n            margin: 0;\n            padding: 0;\n            overflow: hidden;\n            background-color: #333333;\">\n    <div>\n        <div>\n            <li><a th:href=\"@{/}\">Home Page</a></li>\n        </div>\n        <div th:if=\"${#lists.contains(roles, 'ADMIN')}\">\n            <li><a th:href=\"@{/users/crud-user}\">User</a></li>\n        </div>\n\n        <div th:if=\"${#lists.contains(roles, 'ADMIN') ||#lists.contains(roles, 'STUDENT') || #lists.contains(roles, 'TEACHER')}\">\n            <li><a th:href=\"@{/subject}\">Subject</a></li>\n        </div>\n\n        <div th:if=\"${#lists.contains(roles, 'ADMIN') || #lists.contains(roles, 'STUDENT') || #lists.contains(roles, 'TEACHER')}\">\n            <li><a th:href=\"@{/classroom}\">Classroom</a></li>\n        </div>\n\n        <div th:if=\"${#lists.contains(roles,'STUDENT') || #lists.contains(roles,'ADMIN') }\">\n\n            <li><a th:href=\"@{/student}\">Student</a></li>\n        </div>\n        <li><a href=\"#about\">About</a></li>\n        <li style=\"float: right;\"><a th:href=\"@{/login}\">Login</a></li>\n        <li style=\"float: right;\"><a th:href=\"@{/login?logout=true}\">Logout</a></li>\n    </div>\n</ul>\n\n\n\n\n<h1 align=\"center\" style=\"padding: 50px; color: azure\">Subject</h1>\n\n<div align=\"center\" th:if=\"${#lists.contains(roles, 'ADMIN')}\">\n    <button><a th:href=\"@{/subject/insert-subject}\">Insert Subject</a></button>\n\n</div>\n<br/>\n<h1 th:text=\"${filename}\"></h1>\n<table align=\"center\" style=\"width:80%\" border=\"1\" >\n    <thead>\n    <tr>\n        <th>ID</th>\n        <th>Subject Id</th>\n        <th>Subject Name</th>\n        <th>Start Time</th>\n        <th>End Time</th>\n        <th>Day Of Week</th>\n        <th>Document</th>\n        <th>Action</th>\n    </tr>\n    </thead>\n    <tbody>\n    <tr th:each=\"subject:${subjectList}\" align=\"center\">\n        <td th:text=\"${subject.id}\"></td>\n        <td th:text=\"${subject.subjectId}\"></td>\n        <td th:text=\"${subject.name}\"></td>\n        <td th:text=\"${subject.startTime}\"></td>\n        <td th:text=\"${subject.endTime}\"></td>\n        <td th:text=\"${subject.dayOfWeek}\"></td>\n        <td th:text=\"${subject.document}\"></td>\n        <td>\n            <div th:if=\"${#lists.contains(roles, 'TEACHER')}\">\n                <button><a th:href=\"@{/subject/upload-document-{id}(id =${subject.id})}\">Upload Document</a></button>\n            </div>\n            <div th:if=\"${#lists.contains(roles, 'STUDENT')}\">\n                <button><a th:href=\"@{/subject/download-document-{filename}(filename =${subject.document})}\">Download Document</a></button>\n            </div>\n            <div th:if=\"${#lists.contains(roles, 'ADMIN')}\">\n                <button><a th:href=\"@{/subject/update-subject-{id}(id =${subject.id})}\">Update Subject</a></button>\n                <button><a th:href=\"@{/subject/delete-subject-{id}(id =${subject.id})}\">Delete Subject</a></button>\n            </div>\n        </td>\n    </tr>\n   longpc </tbody>\n</table>\n</body>\n" + "</html>").getBytes();
//        response.setContentType("text/html;charset=UTF-8");
//        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(bytes);
        return "crud_subject";
    }

    @GetMapping("/insert-subject")
    public String insert(ModelMap modelMap) {
        modelMap.addAttribute("subject", new SubjectDTO());
        return "insert_subject";
    }

    @PostMapping("/insert-subject")
    public String insertSubject(@ModelAttribute(value = "subject") SubjectDTO subjectDTO, ModelMap modelMap) {

        String inputTimeStart = subjectDTO.getStartTime();
        String inputTimeEnd = subjectDTO.getEndTime();
        boolean check1 = inputTimeStart.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
        boolean check2 = inputTimeEnd.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
        if(subjectDTO.getDayOfWeek() >7 || subjectDTO.getDayOfWeek()<2 || !check1 || !check2  ){
            modelMap.addAttribute("messError", "Day Of Week Or Time is not valid");
            return "redirect:/insert-subject";
        }
        else{

            Subject subject = new Subject();
            subject.setSubjectId(subjectDTO.getSubjectId());
            subject.setName(subjectDTO.getName());
            subject.setStartTime(Time.valueOf(subjectDTO.getStartTime()));
            subject.setEndTime(Time.valueOf(subjectDTO.getEndTime()));
            subject.setDayOfWeek(subjectDTO.getDayOfWeek());
            subjectService.insertSubject(subject);
            modelMap.addAttribute("subjectId", subject.getSubjectId());
            return "redirect:/subject/insert-class-subject";
        }
    }
    @GetMapping("/insert-class-subject")
    public String insertClassSubject(@ModelAttribute(value = "subjectId") String subjectId, ModelMap modelMap){
//        ClassSubject classSubject = classSubjectService.findBySubjectId();

        ClassSubject classSubject = new ClassSubject();
        classSubject.setSubjectId(subjectId);
        modelMap.addAttribute("classSubject", classSubject);
        return "insert_class_subject";
    }
    @PostMapping("/insert-class-subject")
    public String insertClassSubject(@ModelAttribute(value = "classSubject") ClassSubject classSubject,@ModelAttribute(value = "classId") String classId, ModelMap modelMap){

        classSubject.setClassId(classId);
        classSubjectService.insertClassSubject(classSubject);
        return "redirect:/subject";
    }

    @GetMapping("/update-subject-{id}")
    public String updateSubject(@ModelAttribute(value = "id") Integer id, ModelMap modelMap) {
//        Subject subject = subjectService.updateSubject(id);
        SubjectDTO subjectDTO = subjectService.findSubjectDTOById(id);
        modelMap.addAttribute("subject", subjectDTO);
        return "update-subject";
    }

    @PostMapping("/update-subject")
    public String updateSubjectt(@ModelAttribute(value = "subject") SubjectDTO subjectDTO, ModelMap modelMap) {
//        Subject subject = subjectService.findBySubjectId(subjectDTO.getSubjectId());

        String inputTimeStart = subjectDTO.getStartTime();
        String inputTimeEnd = subjectDTO.getEndTime();
        boolean check1 = inputTimeStart.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
        boolean check2 = inputTimeEnd.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$");
        if(subjectDTO.getDayOfWeek() >7 || subjectDTO.getDayOfWeek()<2 || !check1 || !check2  ){
            modelMap.addAttribute("messError", "Day Of Week Or Time is not valid");
            modelMap.addAttribute("id", subjectDTO.getId());
            return "redirect:/subject/update-subject-{id}";
        }
        else{
        Subject subject = subjectService.findBySubjectId(subjectDTO.getSubjectId());
        subject.setName(subjectDTO.getName());
        subject.setStartTime(Time.valueOf(subjectDTO.getStartTime()));
        subject.setEndTime(Time.valueOf(subjectDTO.getEndTime()));
        subject.setDayOfWeek(subjectDTO.getDayOfWeek());
        subjectService.updateSubject(subject.getId());
        return "redirect:/subject";
    }
    }


    @GetMapping("/delete-subject-{id}")
    public String deleteSubject(@ModelAttribute(value = "id") Integer id) {
        subjectService.deleteSubject(id);
        Subject subject = subjectService.findById(id);
        classSubjectService.deleteClassSubject(subject.getSubjectId());
        return "redirect:/subject";
    }

    @GetMapping("/upload-document-{id}")
    public String uploadDocument(ModelMap modelMap, @ModelAttribute(value = "id") Integer id) {
        Subject subject = subjectService.findById(id);
        modelMap.addAttribute("subject", subject);
//        modelMap.addAttribute("id", id);
        return "uploadSubject";
    }

    @PostMapping("/upload-document")
    public String uploadDocument(@RequestParam("file") MultipartFile file, @ModelAttribute(value = "subject") Subject subject, ModelMap modelMap) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long size = file.getSize();

        FileUploadUtil.saveFile(fileName, file);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/myfiles/" + fileName);

        Subject subject1 = subjectService.findById(subject.getId());
        subject1.setDocument(response.getFileName());
        subjectService.insertSubject(subject1);
        modelMap.addAttribute("response", response.getDownloadUri());

        modelMap.addAttribute("filecode", fileName);
        return "redirect:/subject";
    }


    @GetMapping("/download-document-{filename}")
    public void getDocument(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
        byte[] bytes = FileDownloadUtil.getFileAsResource(filename);
        response.setContentType("application/octet-stream");
        response.setHeader("attachment", "filename=\\\"\" + filename + \"\\\"\"");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);

    }
}
