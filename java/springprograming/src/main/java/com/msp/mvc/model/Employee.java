package com.msp.mvc.model;

import lombok.AllArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
//    @NotBlank
    private String Name;

    @NotNull
    @Column(name = "age")
    private Integer Age;

    @Column(name = "email")
    private String Email;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public Employee(Long id, String name, Integer age, String email) {
        this.id = id;
        Name = name;
        Age = age;
        Email = email;
    }

    public Employee() {
    }
}
