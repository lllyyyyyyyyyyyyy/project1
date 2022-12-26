package com.vss.projectstudent2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @NotBlank
    private String userName;

    @NotBlank
    private String password;

//    private UserDTO user;

    private UserUpdatePassword user;
}
