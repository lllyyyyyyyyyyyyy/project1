package com.vss.lynt.service;

import com.vss.lynt.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {


    List<Student> getAll();

    Student findbyId(Integer id);

    Student insertStudent(Student student1);

    Optional<Student> findbyStudentId(Integer studentId);
}
