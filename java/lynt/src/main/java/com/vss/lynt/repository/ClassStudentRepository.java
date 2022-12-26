package com.vss.lynt.repository;

import com.vss.lynt.model.ClassStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassStudentRepository extends JpaRepository<ClassStudent,Integer> {


    List<ClassStudent> findByClassId(String classId);
}
