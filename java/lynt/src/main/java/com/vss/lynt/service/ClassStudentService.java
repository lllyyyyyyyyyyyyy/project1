package com.vss.lynt.service;

import com.vss.lynt.model.ClassStudent;

import java.util.List;

public interface ClassStudentService {

    List<ClassStudent> findAllStudentByClassId(String classId);

    List<ClassStudent> findAllStudent();

}
