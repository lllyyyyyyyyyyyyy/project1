package com.vss.social_webapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String coverPicture;
    private String profilePicture;
    private String name;
    private String password;
    private String dateOfBirth;
    private String email;
    private String phoneNumber;
    private String gender;
//    private String createdBy;
    private String updateBy;
    private String updateAt;
//    private String enabled;

}
