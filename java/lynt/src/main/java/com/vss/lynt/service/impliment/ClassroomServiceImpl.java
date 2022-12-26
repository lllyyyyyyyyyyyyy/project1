package com.vss.lynt.service.impliment;

import com.vss.lynt.model.*;
import com.vss.lynt.repository.ClassSubjectRepository;
import com.vss.lynt.repository.ClassroomRepository;
import com.vss.lynt.repository.SubjectRepository;
import com.vss.lynt.repository.TeacherRepository;
import com.vss.lynt.service.ClassStudentService;
import com.vss.lynt.service.ClassSubjectService;
import com.vss.lynt.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    ClassSubjectRepository classSubjectRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    ClassSubjectService classSubjectService;

    @Autowired
    ClassStudentService classStudentService;

    @Override
    public List<Classroom> getAllSubjectByClass() {

        List<Classroom> classrooms = classroomRepository.findAll();
        for(Classroom i:classrooms) {
            Optional<Teacher> teacher = teacherRepository.findByTeacherId(i.getTeacherId());
            if (teacher.isPresent()) i.setTeacher(teacher.get());
            else i.setTeacher(null);

            List<ClassSubject> classSubjects1 = classSubjectService.findAllSubjectByClassId(i.getClassId());
            if(classSubjects1.isEmpty())
                i.setClassSubjects(null);
            else
                i.setClassSubjects(classSubjects1);

            List<ClassStudent> classStudents = classStudentService.findAllStudentByClassId(i.getClassId());
            if(classStudents.isEmpty()){
                i.setClassStudents(null);
            }
            else{
                i.setClassStudents(classStudents);
            }
        }
        return classrooms;
//        return classroomRepository.getAllSubjectByClass();
    }

    @Override
    public Classroom findById(Integer id) {
        return classroomRepository.findById(id).get();
    }

    @Override
    public Classroom findByClassId(String classId) {
        return classroomRepository.findByClassId(classId).get();
    }

}
