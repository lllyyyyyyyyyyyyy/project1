package com.msp.mvc.service;

import com.msp.mvc.model.Employee;
import com.msp.mvc.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ServicesEmployeeImplement implements ServicesEmployee {

    private EmployeeRepository employeeRepository;

    @Autowired
    public ServicesEmployeeImplement(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> showAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee insertEmployee(Employee employee) {

        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public void deleteEmployee(Long id) {

        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(Long id) {

        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else throw new RuntimeException("id not found!");
        return employee;

    }


    @Override
    public Employee findbyId(Long id) {

        return employeeRepository.findById(id).get();
    }
}
