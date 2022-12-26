package com.vss.projectstudent2.service.impl;

import com.vss.projectstudent2.model.Classroom;
import com.vss.projectstudent2.model.Course;
import com.vss.projectstudent2.repository.ClassroomRepository;
import com.vss.projectstudent2.repository.CourseRepository;
import com.vss.projectstudent2.repository.SubjectRepository;
import com.vss.projectstudent2.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public List<Classroom> getAllClassroomByCourse() {

        List<Classroom> classrooms = classroomRepository.findAll();

        for (Classroom i : classrooms) {
            Optional<Course> course = courseRepository.findByCode(i.getCourseCode());
            if (course.isPresent()) i.setCourse(course.get());
            else i.setCourse(null);
        }
        return classrooms;
    }

    @Override
    public List<Classroom> getListClassroom() {
        return classroomRepository.findAll();
    }

    @Override
    public Classroom insertClass(Classroom classroom) {
        return classroomRepository.save(classroom);
    }

    @Override
    public List<Classroom> findByCourseCode(String courseCode) {
        return classroomRepository.findAllByCourseCode(courseCode);
    }
}
