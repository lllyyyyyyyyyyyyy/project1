package com.msp.mvc.service;

import com.msp.mvc.model.Employee;

import java.util.List;
import java.util.Optional;

public interface ServicesEmployee {

    List<Employee> showAllEmployee();

    Employee insertEmployee(Employee employee);

    void deleteEmployee(Long id);

    Employee updateEmployee(Long id);

    Employee findbyId(Long id);



}
