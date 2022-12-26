package com.vss.projectstudent2.service.impl;

import com.vss.projectstudent2.model.User;
import com.vss.projectstudent2.model.UserDTO;
import com.vss.projectstudent2.model.UserUpdatePassword;
import com.vss.projectstudent2.repository.UserRepository;
import com.vss.projectstudent2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSeviceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User insertUser(UserDTO userDTO) {

        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User update(Long id) {

        Optional<User> user = userRepository.findById(id);
//        user.get().setUserName(user.get().getUserName());
        user.get().setPassword(passwordEncoder.encode(user.get().getPassword()));
        return userRepository.save(user.get());
    }


//    @Override
//    public User updateUser(Long id) {
//        Optional<User> user = userRepository.findById(id);
//        return user.get();
//    }

    @Override
    public boolean checkInsert(String userName, String password) {

        Optional<User> optional = userRepository.findByUserName(userName);
        if(optional.isPresent() && optional.get().getPassword().equals(password)){
            return true;
        }
        return false;
    }

}
