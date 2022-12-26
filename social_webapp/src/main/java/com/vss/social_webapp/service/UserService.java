package com.vss.social_webapp.service;

import com.vss.social_webapp.dtos.UserDTO;
import com.vss.social_webapp.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean ckeckLogin(String userName, String password);

    User findByUsername(String userName);

    User findByPhoneNumber(String phoneNumber);

    Optional<User> findByUserEmail(String userEmail);

    void register(User user, String siteURL) throws MessagingException, UnsupportedEncodingException, com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException, MessagingException;

    boolean verify(String verificationCode);

    void saveUser(User user);

    void updateInfoUser(User user);

    List<User> getAllUser();
}
