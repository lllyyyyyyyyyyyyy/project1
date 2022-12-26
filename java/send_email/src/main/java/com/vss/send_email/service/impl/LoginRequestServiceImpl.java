package com.vss.send_email.service.impl;

import com.vss.send_email.model.LoginRequest;
import com.vss.send_email.repository.LoginRequestRepository;
import com.vss.send_email.service.LoginRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class LoginRequestServiceImpl implements LoginRequestService {
    @Autowired
    LoginRequestRepository loginRequestRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean ckeckLogin(String email, String password) {

        Optional<LoginRequest> optional = loginRequestRepository.findByEmail(email);
        if(optional.isPresent() && bCryptPasswordEncoder.matches(password, optional.get().getPassword())){
            return true;
        }
        return false;
    }
}
