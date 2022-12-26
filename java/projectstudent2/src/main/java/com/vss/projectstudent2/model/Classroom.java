package com.vss.projectstudent2.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "classroom")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "course_code")
    private String courseCode;

    @Transient
    private Course course;

//    @Transient
//    private List<Subject> subjects;
}
