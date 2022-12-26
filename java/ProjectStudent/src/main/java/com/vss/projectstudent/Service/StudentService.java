package com.vss.projectstudent.Service;

import com.vss.projectstudent.Model.Student;
import com.vss.projectstudent.Model.StudentClassDTO;
import com.vss.projectstudent.Model.StudentDTO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface StudentService {

    List<Student> getListStudent();

    Student insertStudent(Student student);

    void deleteStudent(Long id);


    Student updateStudent(Long id);


    List<Student> findStudent(String keyword);


    List<Student> getInfor();

    List<Student> getStudentByCourses(String courses_id);

}
