package com.vss.projectstudent.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Student {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private Date date_of_birth;

    @Column(name = "address")
    private String address;

    @Column(name = "class_id")
    private String class_id;

    @Column(name = "courses_id")
    private String courses_id;
    @Transient
    private Classroom classroom;

    public Student getStudent() {
        return new Student();
    }

//    @Transient
//    private Student student;
}
