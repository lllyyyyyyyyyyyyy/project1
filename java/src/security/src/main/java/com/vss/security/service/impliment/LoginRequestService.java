package com.vss.security.service.impliment;

public interface LoginRequestService {

    boolean ckeckLogin(String userName, String password);
    String checkStatus(String userName);
}
