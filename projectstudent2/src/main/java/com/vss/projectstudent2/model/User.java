package com.vss.projectstudent2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static com.vss.projectstudent2.model.UserEnum.DELETE;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "username")
    private String userName;

    @NotBlank
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="status")
    private UserEnum userEnum;

}
