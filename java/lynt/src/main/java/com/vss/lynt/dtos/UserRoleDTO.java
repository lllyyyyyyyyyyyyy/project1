package com.vss.lynt.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    private Integer id;

//    @Column(name = "user_id")
    private Integer userId;

//    @Column(name = "role_id")
    private Long roleId;
}
