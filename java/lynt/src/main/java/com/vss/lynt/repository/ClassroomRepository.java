package com.vss.lynt.repository;

import com.vss.lynt.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {
    Optional<Classroom> findByClassId(String classId);


    @Query(value = "SELECT *\n" +
            "FROM\n" +
            "    class\n" +
            "LEFT JOIN\n" +
            "    class_subject\n" +
            "ON class.class_id = class_subject.class_id\n" +
            "LEFT JOIN\n" +
            "\tsubject\n" +
            "ON subject.subject_id = class_subject.subject_id",nativeQuery = true)

//    @Query("select c from Classroom c where c.id = 5")
    List<Classroom> getAllSubjectByClass();
}
