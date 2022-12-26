package com.vss.lynt.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;

@Entity
@Table(name = "class_student")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "class_id")
    private String classId;

    @Column(name = "student_id")
    private Integer studentId;

    @Transient
    private Student student;
}
