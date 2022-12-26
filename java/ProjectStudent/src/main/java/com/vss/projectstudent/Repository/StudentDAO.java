package com.vss.projectstudent.Repository;

import com.vss.projectstudent.Model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDAO {


    int save(Student student);

    int update(Student student);

    Optional<Student> findById(Long id);

    int deleteById(Long id);

    List<Student> findAll();


    List<Student> findByName(String name);

    int deleteAll();

}
