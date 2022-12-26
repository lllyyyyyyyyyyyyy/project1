package com.vss.lynt.service.impliment;

import com.vss.lynt.model.LoginRequest;
import com.vss.lynt.repository.LoginRequestRepository;
import com.vss.lynt.service.LoginRequestService;
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
    public boolean ckeckLogin(String userName, String password) {
        Optional<LoginRequest> optional = loginRequestRepository.findByUserName(userName);
        if(optional.isPresent() && bCryptPasswordEncoder.matches(password, optional.get().getPassword())){
            return true;
        }
        return false;
    }
}
