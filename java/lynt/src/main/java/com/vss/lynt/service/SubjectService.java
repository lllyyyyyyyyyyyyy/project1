package com.vss.lynt.service;

import com.vss.lynt.dtos.SubjectDTO;
import com.vss.lynt.model.Subject;

import java.util.List;

public interface SubjectService {

    List<Subject> getAllSubject();
    Subject insertSubject(Subject subject);
    void deleteSubject(Integer id);
    Subject updateSubject(Integer id);
    Subject findById(Integer id);

    Subject findBySubjectId(String subjectId);

    SubjectDTO findSubjectDTOById(Integer id);
}
