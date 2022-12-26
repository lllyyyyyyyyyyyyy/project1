package com.vss.projectstudent.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Courses {

    @Id
    @Column(name = "courses_id")
    private String courses_id;

    @Column(name = "courses_name")
    private String courses_name;

}
