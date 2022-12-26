package com.vss.security.service.impliment;

import com.vss.security.model.User;
import com.vss.security.model.UserDTO;
import com.vss.security.model.UserEnum;
import com.vss.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public User saveUser(UserDTO userDTO) {

        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUserEnum("ACTIVE");
        return userRepository.save(user);
    }

    @Override
    public List<User> getList() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id) {
        Optional<User> user = userRepository.findById(id);
//        user.get().setPassword(passwordEncoder);
        return user.get();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }
}
