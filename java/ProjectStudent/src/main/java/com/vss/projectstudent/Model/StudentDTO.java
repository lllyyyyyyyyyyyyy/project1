package com.vss.projectstudent.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDTO {

    private Long id;

    private String name;

    private Date date_of_birth;

    private String address;

    private String class_id;

//    @Transient
//    private StudentDTO studentDTO;

}
