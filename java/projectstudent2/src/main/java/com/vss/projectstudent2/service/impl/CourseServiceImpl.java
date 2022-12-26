package com.vss.projectstudent2.service.impl;

import com.vss.projectstudent2.model.Course;
import com.vss.projectstudent2.repository.CourseRepository;
import com.vss.projectstudent2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> courseList() {
        return courseRepository.findAll();
    }

    @Override
    public Course insertCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.get();
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> findCourse(String keyword) {
        return courseRepository.findCourse(keyword);
    }
}
