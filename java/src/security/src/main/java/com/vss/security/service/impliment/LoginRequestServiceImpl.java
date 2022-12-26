package com.vss.security.service.impliment;

import com.vss.security.model.LoginRequest;
import com.vss.security.model.User;
import com.vss.security.model.UserEnum;
import com.vss.security.repository.LoginRequestRepository;
import com.vss.security.repository.UserRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LoginRequestServiceImpl implements LoginRequestService{

    @Autowired
    LoginRequestRepository loginRequestRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;
    @Override
    public boolean ckeckLogin(String userName, String password) {
        Optional<LoginRequest> optional = loginRequestRepository.findByUserName(userName);
        if(optional.isPresent() && bCryptPasswordEncoder.matches(password, optional.get().getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public String checkStatus(String userName) {
        String text;
        User user = userRepository.findByUserName(userName);
        if(user.getUserEnum().equals(UserEnum.DELETE)){
            text = "Tai khoan bi xoa roi!";
        }
        else if (user.getUserEnum().equals(UserEnum.UNACTIVE)) {
            text = "Tai khoan bi khoa roi!";
        }
        else{
            text = "ACTIVE";
        }
        return text;
    }
}
