package com.vss.projectstudent2.service;

import com.vss.projectstudent2.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getInforStudentByClass();

    List<Student> getStudent();
}
