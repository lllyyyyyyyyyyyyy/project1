package com.vss.projectstudent.Service.impl;

import com.vss.projectstudent.Model.Classroom;
import com.vss.projectstudent.Model.Student;
import com.vss.projectstudent.Repository.ClassroomRepository;
import com.vss.projectstudent.Repository.StudentRepository;
import com.vss.projectstudent.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentSeviceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    @Override
    public List<Student> getListStudent() {
        return studentRepository.findAll();
    }

    @Override
    public Student insertStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Student updateStudent(Long id) {
        Optional<Student> optional = studentRepository.findById(id);
        Student student = null;
        if (optional.isPresent()) {
            student = optional.get();
        } else throw new RuntimeException("id not found!");
        return student;
    }

    @Override
    public List<Student> findStudent(String keyword) {


        if(keyword != null){
            return studentRepository.findStudent(keyword);
        }
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    public List<Student> getInfor() {
        List<Student> list = new ArrayList<>();
        list = studentRepository.findAll();
        for(Student item : list){
            Optional<Classroom> op = classroomRepository.findById(item.getClass_id());
            if (op.isPresent()){
                item.setClassroom(op.get());
            } else {
                item.setClassroom(null);
            }
        }
        return list;
    }

    @Override
    public List<Student> getStudentByCourses(String courses_id) {
        return studentRepository.getStudentsByCourses_id(courses_id);
    }
}
