package com.vss.lynt.service.impliment;

import com.vss.lynt.model.User;
import com.vss.lynt.dtos.UserDTO;
import com.vss.lynt.repository.UserRepository;
import com.vss.lynt.service.UserService;
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
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User insertUser(UserDTO userDTO) {

        User user = new User();
        String name = userDTO.getUserId()+"@vnu.edu.vn";
        user.setUserName(name);
        user.setUserId(userDTO.getUserId());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {

    }

    @Override
    public User update(Integer id) {
        Optional<User> user = userRepository.findByUserId(id);

        return user.get();
    }

    @Override
    public Optional<User> findByUserId(Integer userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public User insert(User user, boolean isEnable) {
        User user1 = new User();
        user1.setId(user.getId());
        user1.setUserName(user.getUserName());
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user1.setUserId(user.getUserId());
        user1.setEnabled(isEnable);
        return userRepository.save(user1);
    }


}
