package com.vss.lynt.repository;

import com.vss.lynt.model.LoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LoginRequestRepository extends JpaRepository<LoginRequest,Long> {
//    @Query(value = "SELECT * FROM users WHERE users.user_id = ?",nativeQuery = true)
//    Optional<LoginRequest> findUserId(Long userId);

    Optional<LoginRequest> findByUserName(String userName);
}
