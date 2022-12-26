package com.vss.lynt.service;

import com.vss.lynt.model.ClassSubject;

import java.util.List;

public interface ClassSubjectService {
    List<ClassSubject> getAllClassAndSubject();

    List<ClassSubject> findAllSubjectByClassId(String classId);

    void deleteClassSubject(String subjectId);

    ClassSubject insertClassSubject(ClassSubject classSubject);

//    List<ClassSubject> findBySubjectId(String subjectId);
}
