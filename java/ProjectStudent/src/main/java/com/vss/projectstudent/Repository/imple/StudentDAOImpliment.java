package com.vss.projectstudent.Repository.imple;

import com.vss.projectstudent.Model.Student;
import com.vss.projectstudent.Repository.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public class StudentDAOImpliment implements StudentDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SAVE_EMPLOYEE = "INSERT INTO Employee(Employee.Name, Employee.Age, Employee.Email) VALUES(?,?,?)";

    private static final String UPDATE_EMPLOYEE = "UPDATE Employee SET Name=?, Age=?, Email=? WHERE id=?";

    private static final String FIND_BY_ID = "SELECT * FROM Employee WHERE id = ?";

    private static final String DELETE_BY_ID = "DELETE FROM Employee WHERE id=?";

    private static final String FIND_ALL = "SELECT * FROM Employee";

    private static final String FIND_BY_NAME = "SELECT * FROM Employee WHERE Name = ?";

    private static final String DELETE_ALL = "DELETE FROM Employee";

    @Override
    public int save(Student student) {
        return jdbcTemplate.update(SAVE_EMPLOYEE, new Object[]{null, student.getDate_of_birth(), student.getAddress(), student.getClass_id()});
    }

    @Override
    public int update(Student student) {
        return jdbcTemplate.update(UPDATE_EMPLOYEE, new Object[]{student.getId(), null, student.getDate_of_birth(), student.getAddress(), student.getClass_id()});
    }

    @Override
    public Optional<Student> findById(Long id){

        Student employee = jdbcTemplate.queryForObject(FIND_BY_ID, BeanPropertyRowMapper.newInstance(Student.class), id);
        return Optional.ofNullable(employee);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(FIND_ALL, BeanPropertyRowMapper.newInstance(Student.class));
    }

    @Override
    public List<Student> findByName(String name) {
        return jdbcTemplate.query(FIND_BY_NAME, BeanPropertyRowMapper.newInstance(Student.class), name);
    }

    @Override
    public int deleteAll() {
        return jdbcTemplate.update(DELETE_ALL);
    }

}
