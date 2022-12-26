package com.vss.projectstudent2.service.impl;

import com.vss.projectstudent2.model.LoginRequest;
import com.vss.projectstudent2.repository.LoginRequestRepository;
import com.vss.projectstudent2.service.LoginRequestService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
@Service
public class LoginRequestServiceImpl implements LoginRequestService {

   @Autowired
    LoginRequestRepository loginRequestRepository;

    @Override
    public List<LoginRequest> getAll() {
        return loginRequestRepository.findAll();
    }

    @Override
    public boolean ckeckLogin(String userName, String password) {

        Optional<LoginRequest> optional = loginRequestRepository.findByUserName(userName);
        if(optional.isPresent() && optional.get().getPassword().equals(password)){
            return true;
        }
        return false;
    }

    @Override
    public String encryptPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] digest = messageDigest.digest(password.getBytes());
        BigInteger bigInteger = new BigInteger(1, digest);
        return bigInteger.toString(16);
    }
}
