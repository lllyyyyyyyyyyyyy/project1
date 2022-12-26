package com.vss.projectstudent2.service;

import com.vss.projectstudent2.model.LoginRequest;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface LoginRequestService {

    List<LoginRequest> getAll();

    boolean ckeckLogin(String userName, String password);

    String encryptPassword(String password) throws NoSuchAlgorithmException;
}
