package com.vss.security.service.impliment;


import com.vss.security.model.User;
import com.vss.security.model.UserDTO;

import java.util.List;

public interface UserService {
    User saveUser(UserDTO userDTO);

    List<User> getList();

    User updateUser(Long id);

    User save(User user);

    User findByUserName(String userName);

    User findById(Long id);
}
