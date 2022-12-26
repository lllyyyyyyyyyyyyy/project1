package com.vss.lynt.service.impliment;

import com.vss.lynt.dtos.SubjectDTO;
import com.vss.lynt.model.Subject;
import com.vss.lynt.repository.SubjectRepository;
import com.vss.lynt.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public List<Subject> getAllSubject() {
        return subjectRepository.findAll();
    }


    @Override
    public Subject insertSubject(Subject subject) {
        Subject subject1 = subjectRepository.save(subject);
        return subject1;
    }

    @Override
    public void deleteSubject(Integer id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public Subject updateSubject(Integer id) {
        Subject subject = subjectRepository.findById(id).get();
//        subject.setSubjectId();
        return subjectRepository.save(subject);
    }

    @Override
    public Subject findById(Integer id) {
        return subjectRepository.findById(id).get();
    }

    @Override
    public Subject findBySubjectId(String subjectId) {
        return subjectRepository.findBySubjectId(subjectId).get();
    }

    @Override
    public SubjectDTO findSubjectDTOById(Integer id) {
        Subject subject = subjectRepository.findById(id).get();
        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setId(subject.getId());
        subjectDTO.setSubjectId((subject.getSubjectId()));
        subjectDTO.setName(subject.getName());
        subjectDTO.setStartTime(String.valueOf(subject.getStartTime()));
        subjectDTO.setEndTime(String.valueOf(subject.getEndTime()));
        subjectDTO.setDayOfWeek(subject.getDayOfWeek());
        return subjectDTO;
    }
}
