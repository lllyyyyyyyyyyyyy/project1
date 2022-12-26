package com.vss.lynt.repository;

import com.vss.lynt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query(value = "SELECT * FROM users WHERE users.user_name = ?1",nativeQuery = true)
    public User getUserByUserName(String userName);

    @Query(value = "SELECT * FROM users",nativeQuery = true)
    public List<User> findAllCus();

    public Optional<User> findByUserId(Integer userId);
}
