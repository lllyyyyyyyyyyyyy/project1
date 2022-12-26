package com.vss.projectstudent.Repository;

import com.vss.projectstudent.Model.Student;
import com.vss.projectstudent.Model.StudentClassDTO;
import com.vss.projectstudent.Model.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {


    public static final String FIND_ID = "AND id = ?";

    public static final String FIND_NAME = "AND name =?";

    public static final String FIND_DATE = "AND date_of_birth = ?";

    public static final String FIND_ADDRESS = "AND address = ?";

    public static final String FIND_CLASS_ID = "AND class_id = ?";
    public static final String SQl_FIND =  "SELECT * FROM student WHERE 1=1"+
            " AND (CASE id WHEN student.getId() != null THEN id = @id END"+
            " AND (CASE name WHEN student.getName() != null THEN name = @name END" +
            " AND (CASE address WHEN student.getAddress() != null THEN address = @address END" +
            " AND (CASE class_id WHEN student.getClass_id() != null THEN class_id = @class_id END";


    public static final String FIND = "SELECT * FROM student WHERE CONCAT(student.id,' ',student.name,' ', student.date_of_birth,' ', student.address,' ',student.class_id ) LIKE %?1%";
    @Query(value = FIND, nativeQuery = true)
    List<Student> findStudent (String keyword);



    //Select * from student where name = ?
    List<Student> findByName (String name);


    public static final String FIND_BY_CLASS_ID = "SELECT * " +
            " FROM student s " +
            " INNER JOIN class c " +
            " ON s.class_id = c.class_id ";
    @Query(value = FIND_BY_CLASS_ID,nativeQuery = true)
   List<Student> getInformation();



    List<Student> getStudentsByCourses_id(String courses_id);




}
