package com.vss.lynt.repository;

import com.vss.lynt.model.ClassSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassSubjectRepository extends JpaRepository<ClassSubject,Integer> {

    @Query(value = "SELECT * FROM class c, class_subject cs, subject s WHERE c.class_id = cs.class_id AND cs.subject_id = s.subject_id;",nativeQuery = true)
    List<ClassSubject> getAllInfor();

    List<ClassSubject> findByClassId(String classId);

    List<ClassSubject> findBySubjectId(String subjectId);
}
