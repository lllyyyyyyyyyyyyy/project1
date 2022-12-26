package com.vss.lynt.service;

import com.vss.lynt.model.Classroom;

import java.util.List;

public interface ClassroomService {
    List<Classroom> getAllSubjectByClass();

    Classroom findById(Integer id);

    Classroom findByClassId(String classId);
}
