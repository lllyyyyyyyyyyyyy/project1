package com.vss.social_webapp.service;

import com.vss.social_webapp.model.UserGroup;

import java.util.List;
import java.util.Optional;

public interface UserGroupService {
    void saveUserGroup(UserGroup userGroup);

    Optional<UserGroup> findByUserEmail(String userEmail);

    List<UserGroup> getAllGroup();

    List<UserGroup> getAllInfoGroup();

    List<UserGroup> findByGroupCode(String groupCode);

    boolean checkJoinGroup(String userEmail, String groupCode);

}
