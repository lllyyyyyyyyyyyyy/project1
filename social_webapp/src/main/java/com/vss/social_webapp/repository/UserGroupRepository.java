package com.vss.social_webapp.repository;

import com.vss.social_webapp.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup,Long> {
    Optional<UserGroup> findByUserEmail(String userEmail);

    List<UserGroup> findByGroupCode(String groupCode);

    Optional<UserGroup> findByGroupCodeAndUserEmail(String groupCode, String userEmail);

}
