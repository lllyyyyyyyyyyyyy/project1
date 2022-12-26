package com.vss.lynt.controller;

import com.vss.lynt.file.ScheduleExcelExporter;
import com.vss.lynt.file.StudentOfClassExcelExport;
import com.vss.lynt.file.UserExcelExporter;
import com.vss.lynt.model.*;
import com.vss.lynt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    ClassSubjectService classSubjectService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    ClassroomService classroomService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    StudentService studentService;

    @Autowired
    ClassStudentService classStudentService;
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
        List<Classroom> classrooms = classroomService.getAllSubjectByClass();
        List<ClassSubject> classSubjects = classSubjectService.getAllClassAndSubject();
        modelMap.addAttribute("classAndSubject", classSubjects);
        modelMap.addAttribute("classList",classrooms);
        return "crud_class";
    }
    @GetMapping("/get-schedule/{id}")
    public String getSchedule(@PathVariable(value = "id") Integer id, ModelMap modelMap){

        Classroom classroom = classroomService.findById(id);
        List<ClassSubject> classSubjects = classSubjectService.findAllSubjectByClassId(classroom.getClassId());
        for(ClassSubject classSubject:classSubjects){
            int idSchedule = 1;
            while (idSchedule <= 14){
                Schedule schedule = scheduleService.findById(idSchedule).get();
                Time timeStart = classSubject.getSubject().getStartTime();
                Time timeEnd = classSubject.getSubject().getEndTime();
                Time timeSchedule = schedule.getTime();
                Long dayOfWeek = classSubject.getSubject().getDayOfWeek();
                if(timeStart.compareTo(timeSchedule) == 0 || ((timeStart.compareTo(timeSchedule) == -1) && (timeEnd.compareTo(timeSchedule) == 1)) || (timeEnd.compareTo(timeSchedule) == 0)){
                    if (dayOfWeek == 2) {
                        schedule.setMon(classSubject.getSubject().getName());
                    } else if (dayOfWeek == 3) {
                        schedule.setTue(classSubject.getSubject().getName());
                    } else if (dayOfWeek == 4) {
                        schedule.setWed(classSubject.getSubject().getName());
                    } else if (dayOfWeek == 5) {
                        schedule.setThu(classSubject.getSubject().getName());
                    } else if (dayOfWeek == 6) {
                        schedule.setFri(classSubject.getSubject().getName());
                    } else if (dayOfWeek == 7) {
                        schedule.setSat(classSubject.getSubject().getName());
                    }
                }
                else {
                    if (dayOfWeek == 2) {
                        schedule.setMon("--");
                    } else if (dayOfWeek == 3) {
                        schedule.setTue("--");
                    } else if (dayOfWeek == 4) {
                        schedule.setWed("--");
                    } else if (dayOfWeek == 5) {
                        schedule.setThu("--");
                    } else if (dayOfWeek == 6) {
                        schedule.setFri("--");
                    } else if (dayOfWeek == 7) {
                        schedule.setSat("--");
                    }
                }
                idSchedule ++;
                scheduleService.insertSchedule(schedule);
            }
        }
        List<Schedule> schedules = scheduleService.findAll();
        modelMap.addAttribute("schedule",schedules);
        modelMap.addAttribute("className", classroom.getName());

        return "schedule_table";
    }

    @GetMapping("/download")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<Schedule> schedules = scheduleService.findAll();
        ScheduleExcelExporter excelExporter = new ScheduleExcelExporter(schedules);
        excelExporter.export(response);
//        return "schedule_table";
    }

    @GetMapping("/student/{classId}")
    public void downloadStudent(@PathVariable(value = "classId") String classId,ModelMap modelMap, HttpServletResponse response) throws IOException {
//        Classroom classroom = classroomService.findByClassId(classId);
        List<ClassStudent> classStudents = classStudentService.findAllStudentByClassId(classId);
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=student_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        StudentOfClassExcelExport studentOfClassExcelExport = new StudentOfClassExcelExport(classStudents);
        studentOfClassExcelExport.export(response);
    }


}
