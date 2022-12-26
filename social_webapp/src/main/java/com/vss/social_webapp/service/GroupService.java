package com.vss.social_webapp.service;

import com.vss.social_webapp.model.Group;

import java.util.List;

public interface GroupService {
    Group saveGroup(Group group);

    List<Group> getInforOfUsersGroup();

    Group findByCode(String code);

    Group getInforByCode(String code);
}
