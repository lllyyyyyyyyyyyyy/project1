package com.vss.projectstudent.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
public class StudentClassDTO {

    private String class_id;
    private String class_name;
    private String teacher_id;
    private String name;
    private Date date_of_birth;
    private String address;
    private Long id;

    public StudentClassDTO(String class_id, String class_name, String teacher_id, String name, Date date_of_birth, String address, Long id) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.teacher_id = teacher_id;
        this.name = name;
        this.date_of_birth = date_of_birth;
        this.address = address;
        this.id = id;
    }
}
