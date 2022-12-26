package com.vss.lynt.service;

import com.vss.lynt.model.User;
import com.vss.lynt.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUser();
    User insertUser(UserDTO userDTO);
    void deleteUser(Integer id);
    User update(Integer id);
    Optional<User> findByUserId(Integer userId);
    void deleteAll();

    User insert(User user, boolean isEnable);
}
