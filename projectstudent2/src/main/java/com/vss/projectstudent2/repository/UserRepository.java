package com.vss.projectstudent2.repository;

import com.vss.projectstudent2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteById(Long id);

    Optional<User> findById(Long id);

    Optional<User> findByUserName(String userName);

//    public final static String INSERT= "INSERT INTO users VALUE(?1, ?2)";

}
