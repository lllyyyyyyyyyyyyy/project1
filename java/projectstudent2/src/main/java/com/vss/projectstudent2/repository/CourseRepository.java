package com.vss.projectstudent2.repository;

import com.vss.projectstudent2.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);


    public static final String FIND = "SELECT * FROM course WHERE CONCAT(course.id,' ',course.code,' ', course.name) LIKE %?%";
    @Query(value = FIND, nativeQuery = true)
    List<Course> findCourse (String keyword);

    @Query(value = "SELECT * from course, classroom where course.code = classroom.code",nativeQuery = true)
    List<Course> getall();
}
