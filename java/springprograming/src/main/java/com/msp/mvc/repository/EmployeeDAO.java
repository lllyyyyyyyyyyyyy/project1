package com.msp.mvc.repository;

import com.msp.mvc.model.Employee;

import java.util.List;
import java.util.Optional;


public interface EmployeeDAO {

    int save(Employee employee);

    int update(Employee employee);

    Optional<Employee> findById(Long id);

    int deleteById(Long id);

    List<Employee> findAll();


    List<Employee> findByName(String name);

    int deleteAll();


}
