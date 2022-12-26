package com.vss.lynt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "class")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "class_id")
    private String classId;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "class_subject",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects = new ArrayList<>();

    @Transient
    public Teacher teacher;

    @Transient
    public List<ClassSubject> classSubjects = new ArrayList<>();

    @Transient
    public List<ClassStudent> classStudents = new ArrayList<>();
}
