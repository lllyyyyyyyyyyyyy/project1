package com.vss.projectstudent2.repository;

import com.vss.projectstudent2.model.Course;
import com.vss.projectstudent2.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
    List<Subject> findSubjectByClassroomCode(String classCode);

    public static final String FIND = "select * from subject s inner join classroom c on s.classroom_code = c.code where c.name like '%khoa 62%';";
    @Query(value = FIND, nativeQuery = true)
    List<Subject> findSubject (String keyword);

}
