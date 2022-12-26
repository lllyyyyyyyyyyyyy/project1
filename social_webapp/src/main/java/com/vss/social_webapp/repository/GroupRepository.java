package com.vss.social_webapp.repository;

import com.vss.social_webapp.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByCode(String code);
//    @Query(value = "insert into `groups`(code, name) values (?1, ?2)", nativeQuery = true)
//    public Group saveGroup(String code, String name);
}
