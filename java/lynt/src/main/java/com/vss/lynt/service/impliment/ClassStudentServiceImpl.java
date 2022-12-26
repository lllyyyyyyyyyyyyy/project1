package com.vss.lynt.service.impliment;

import com.vss.lynt.model.ClassStudent;
import com.vss.lynt.model.Student;
import com.vss.lynt.repository.ClassStudentRepository;
import com.vss.lynt.repository.ClassSubjectRepository;
import com.vss.lynt.repository.StudentRepository;
import com.vss.lynt.service.ClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassStudentServiceImpl implements ClassStudentService {

    @Autowired
    ClassStudentRepository classStudentRepository;

    @Autowired
    StudentRepository studentRepository;
    @Override
    public List<ClassStudent> findAllStudentByClassId(String classId) {

        List<ClassStudent> classStudents = classStudentRepository.findByClassId(classId);

        for(ClassStudent i:classStudents){
            Optional<Student> student = studentRepository.findByStudentId(i.getStudentId());
            if(student.isPresent()){
                i.setStudent(student.get());
            }
            else{
                i.setStudent(null);
            }
        }
        return classStudents;
    }

    @Override
    public List<ClassStudent> findAllStudent() {

        List<ClassStudent> classStudents = classStudentRepository.findAll();

        for(ClassStudent i:classStudents){
            Optional<Student> student = studentRepository.findByStudentId(i.getStudentId());
            if(student.isPresent()){
                i.setStudent(student.get());
            }
            else{
                i.setStudent(null);
            }
        }
        return classStudents;
    }
}
