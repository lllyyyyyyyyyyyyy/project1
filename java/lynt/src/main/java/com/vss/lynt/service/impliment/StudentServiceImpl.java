package com.vss.lynt.service.impliment;

import com.vss.lynt.model.Student;
import com.vss.lynt.repository.StudentRepository;
import com.vss.lynt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findbyId(Integer id) {
        return studentRepository.findById(id).get();
    }

    @Override
    public Student insertStudent(Student student1) {
        return studentRepository.save(student1);
    }

    @Override
    public Optional<Student> findbyStudentId(Integer studentId) {
        return studentRepository.findByStudentId(studentId);
    }
}
