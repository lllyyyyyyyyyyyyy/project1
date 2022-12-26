package com.vss.projectstudent.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table (name = "class")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Classroom {
    @Id
    @Column(name = "class_id")
    private String class_id;

    @Column(name = "class_name")
    private String class_name;

    @Column(name = "subject_id")
    private String subject_id;

//    @OneToMany(targetEntity = Student.class,cascade = CascadeType.ALL)
//    @JoinColumn(name = "join" ,referencedColumnName = "class_id")
//    private List<Student> students;

}
