package com.vss.lynt.service.impliment;

import com.vss.lynt.model.ClassSubject;
import com.vss.lynt.model.Classroom;
import com.vss.lynt.model.Subject;
import com.vss.lynt.repository.ClassSubjectRepository;
import com.vss.lynt.repository.ClassroomRepository;
import com.vss.lynt.repository.SubjectRepository;
import com.vss.lynt.service.ClassSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassSubjectServiceImpl implements ClassSubjectService {
    @Autowired
    ClassSubjectRepository classSubjectRepository;

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    SubjectRepository subjectRepository;
    @Override
    public List<ClassSubject> getAllClassAndSubject() {
        List<ClassSubject> classSubjects = classSubjectRepository.findAll();
        for(ClassSubject i: classSubjects){
            Optional<Classroom> classroom = classroomRepository.findByClassId(i.getClassId());
            if(classroom.isPresent()){
                i.setClassroom(classroom.get());
            }
            else{
                i.setClassroom(null);
            }
        }
        for(ClassSubject i:classSubjects){
            Optional<Subject> subject = subjectRepository.findBySubjectId(i.getSubjectId());
            if(subject.isPresent()){
                i.setSubject(subject.get());
            }
            else{
                i.setSubject(null);
            }
        }
        return classSubjects;
    }

    @Override
    public List<ClassSubject> findAllSubjectByClassId(String classId) {

        List<ClassSubject> classSubjects = classSubjectRepository.findByClassId(classId);

        for(ClassSubject i: classSubjects){
            Optional<Subject> subject  = subjectRepository.findBySubjectId(i.getSubjectId());
            if(subject.isPresent())     i.setSubject(subject.get());
            else    i.setSubject(null);
        }

        return classSubjects;
    }

    @Override
    public void deleteClassSubject(String subjectId) {
        List<ClassSubject> classSubjects = classSubjectRepository.findBySubjectId(subjectId);
        classSubjectRepository.deleteAll(classSubjects);
    }

    @Override
    public ClassSubject insertClassSubject(ClassSubject classSubject) {
        return classSubjectRepository.save(classSubject);
    }

//    @Override
//    public List<ClassSubject> findBySubjectId(String subjectId) {
//
//
//
//    }
}
