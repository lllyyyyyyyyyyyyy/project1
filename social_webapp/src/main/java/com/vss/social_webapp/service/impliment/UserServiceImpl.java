package com.vss.social_webapp.service.impliment;

import com.vss.social_webapp.common.CommonConst;
import com.vss.social_webapp.dtos.UserDTO;
import com.vss.social_webapp.model.User;
import com.vss.social_webapp.model.UserGroup;
import com.vss.social_webapp.repository.UserGroupRepository;
import com.vss.social_webapp.repository.UserRepository;
import com.vss.social_webapp.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean ckeckLogin(String userName, String password) {

        User user = userRepository.findByUserName(userName);
        if (user == null || !bCryptPasswordEncoder.matches(password, user.getPassword()))
            return false;

        return true;
    }

    @Override
    public User findByUsername(String userName) {
        User user = new User();
        if(userName.matches(CommonConst.Regex.EMAIL)){
            user = userRepository.findByEmail(userName).get();
            user.setUserName(userName);
        } else if (userName.matches(CommonConst.Regex.PHONE)) {
            user = userRepository.findByPhoneNumber(userName).get();
            user.setUserName(userName);
        }
        userRepository.save(user);
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).get();
    }

    @Override
    public Optional<User> findByUserEmail(String userEmail) {
            return userRepository.findByEmail(userEmail);
    }

    @Override
    public void register(User user, String siteURL) throws javax.mail.MessagingException, UnsupportedEncodingException {

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        userRepository.save(user);
        sendVerificationEmail(user, siteURL);
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL) throws UnsupportedEncodingException, javax.mail.MessagingException {
        String toAddress = user.getEmail();
        String fromAddress = "lynguyen1331999@gmail.com";
        String senderName = "ly";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getName());
        String verifyURL = siteURL + "/user/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        javaMailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateInfoUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {

        List<User> users = userRepository.findAll();
        return users;
    }


}
