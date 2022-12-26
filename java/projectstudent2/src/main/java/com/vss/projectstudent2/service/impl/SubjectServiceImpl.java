package com.vss.projectstudent2.service.impl;

import com.vss.projectstudent2.model.Classroom;
import com.vss.projectstudent2.model.Subject;
import com.vss.projectstudent2.repository.ClassroomRepository;
import com.vss.projectstudent2.repository.SubjectRepository;
import com.vss.projectstudent2.service.ClassroomService;
import com.vss.projectstudent2.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {



    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    ClassroomRepository classroomRepository;
    @Override
    public List<Subject> getSubjectByClass() {
        List<Subject> subjects = subjectRepository.findAll();
        for(Subject i: subjects){
            Optional<Classroom> optional = classroomRepository.findByCode(i.getClassroomCode());
            if(optional.isPresent())    i.setClassroom(optional.get());
            else i.setClassroom(null);
        }
        return subjects;
    }

    @Override
    public List<Subject> findSubjectAndClass(String keyword) {

        List<Subject> subjects = subjectRepository.findAll();
        for(Subject item :subjects){
            Optional<Classroom> classroom = classroomRepository.findByCode(item.getClassroomCode());
            item.setClassroom(classroom.get());
        }
        subjects = subjectRepository.findSubject(keyword);
        return subjects;
    }

    @Override
    public Subject insertSubject(Subject subject) {
        Subject obj = subjectRepository.save(subject);
        Optional<Classroom> classroom = classroomRepository.findByCode(obj.getClassroomCode());
        obj.setClassroom(classroom.get());
        return obj;
    }


}
