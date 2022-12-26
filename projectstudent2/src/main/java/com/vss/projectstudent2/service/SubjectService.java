package com.vss.projectstudent2.service;

import com.vss.projectstudent2.model.Student;
import com.vss.projectstudent2.model.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> getSubjectByClass();

    List<Subject> findSubjectAndClass(String keyword);

    Subject insertSubject(Subject subject);



}
