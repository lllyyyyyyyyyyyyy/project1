package com.vss.projectstudent2.service;

import com.vss.projectstudent2.model.Course;

import java.util.List;

public interface CourseService {

    List<Course> courseList();

    Course insertCourse(Course course);

    Course updateCourse(Long id);

    void deleteCourse(Long id);

    List<Course> findCourse(String keyword);

}
