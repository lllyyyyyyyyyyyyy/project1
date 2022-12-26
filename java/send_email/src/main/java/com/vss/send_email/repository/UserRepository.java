package com.vss.send_email.repository;

import com.vss.send_email.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM user_login_with_email WHERE user_login_with_email.verification_code = ?1",nativeQuery = true)
    public User findByVerificationCode(String code);
}
