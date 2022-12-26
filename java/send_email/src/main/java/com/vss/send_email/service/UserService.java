package com.vss.send_email.service;

import com.vss.send_email.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface UserService {

    void register(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String verificationCode);
}
