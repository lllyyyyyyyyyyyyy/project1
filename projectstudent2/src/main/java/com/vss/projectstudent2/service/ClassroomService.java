package com.vss.projectstudent2.service;

import com.vss.projectstudent2.model.Classroom;
import com.vss.projectstudent2.model.Subject;

import java.util.List;
import java.util.Optional;

public interface ClassroomService {
    List<Classroom> getAllClassroomByCourse();

    List<Classroom> getListClassroom();

    Classroom insertClass(Classroom classroom);

    List<Classroom> findByCourseCode(String courseCode);
}
