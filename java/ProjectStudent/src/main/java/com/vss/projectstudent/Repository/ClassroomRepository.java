package com.vss.projectstudent.Repository;

import com.vss.projectstudent.Model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassroomRepository extends JpaRepository<Classroom,String> {




}
