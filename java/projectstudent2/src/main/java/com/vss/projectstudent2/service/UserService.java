package com.vss.projectstudent2.service;

import com.vss.projectstudent2.model.User;
import com.vss.projectstudent2.model.UserDTO;
import com.vss.projectstudent2.model.UserUpdatePassword;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUser();
    User insertUser(UserDTO userDTO);
    void deleteUser(Long id);
    User update(Long id);

    boolean checkInsert(String userName, String password);


}
