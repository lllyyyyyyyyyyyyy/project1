package com.vss.social_webapp.repository;

import com.vss.social_webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);


//    @Query(value = "SELECT * FROM users WHERE user_email = ?1 OR phone_number = ?1", nativeQuery = true)
//    User findByUserNamee(String userName);

    User findByUserName(String userName);
    User findByVerificationCode(String verificationCode);

    Optional<User> findByEmail(String email);

//    User findByPhoneNumber();
}
