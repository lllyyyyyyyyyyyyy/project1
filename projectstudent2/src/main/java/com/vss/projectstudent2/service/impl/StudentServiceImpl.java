package com.vss.projectstudent2.service.impl;

import com.vss.projectstudent2.model.Classroom;
import com.vss.projectstudent2.model.Student;
import com.vss.projectstudent2.repository.ClassroomRepository;
import com.vss.projectstudent2.repository.CourseRepository;
import com.vss.projectstudent2.repository.StudentRepository;
import com.vss.projectstudent2.service.ClassroomService;
import com.vss.projectstudent2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

   @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private StudentRepository studentRepository;


    @Override
    public List<Student> getInforStudentByClass() {

        List<Student> students = studentRepository.findAll();
        for(Student i: students){
            Optional<Classroom> classroom = classroomRepository.findByCode(i.getClassroomCode());
            if(classroom.isPresent()){
                i.setClassroom(classroom.get());
            }
            else{
                i.setClassroom(null);
            }
        }
        return students;
    }

    @Override
    public List<Student> getStudent() {
        return studentRepository.findAll();
    }
}
