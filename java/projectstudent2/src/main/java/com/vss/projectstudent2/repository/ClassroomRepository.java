package com.vss.projectstudent2.repository;

import com.vss.projectstudent2.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    Optional<Classroom> findByCode(String code);

    public static final String FIND = "SELECT * FROM classroom WHERE CONCAT(classroom.id,' ',classroom.code,' ', classroom.name,' ',classroom.course_code) LIKE %?%";

    @Query(value = FIND, nativeQuery = true)
    List<Classroom> findClassroom(String keyword);

    List<Classroom> findAllByCourseCode(String curseCode);
}
