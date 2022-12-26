package com.vss.lynt.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "class_subject")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "class_id")
    private String classId;

    @Column(name = "subject_id")
    private String subjectId;

    @Transient
    private Classroom classroom;


    @Transient
    private Subject subject;
}
