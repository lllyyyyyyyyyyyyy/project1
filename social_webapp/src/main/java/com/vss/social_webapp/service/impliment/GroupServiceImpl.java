package com.vss.social_webapp.service.impliment;

import com.vss.social_webapp.model.Group;
import com.vss.social_webapp.model.UserGroup;
import com.vss.social_webapp.repository.GroupRepository;
import com.vss.social_webapp.repository.UserGroupRepository;
import com.vss.social_webapp.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserGroupRepository userGroupRepository;
    @Override
    public Group saveGroup(Group group) {
       return groupRepository.save(group);
    }

    @Override
    public List<Group> getInforOfUsersGroup() {

        List<Group> groups = groupRepository.findAll();
        for(Group group:groups){
            List<UserGroup> userGroups = userGroupRepository.findByGroupCode(group.getCode());
            if(userGroups.isEmpty()){
                group.setUserGroups(null);
            }
            else {
                group.setUserGroups(userGroups);
            }
        }
        return groupRepository.saveAll(groups);
    }

    @Override
    public Group findByCode(String code) {
        Optional<Group> group = groupRepository.findByCode(code);
        if (group.isPresent()){
            List<UserGroup> userGroups = userGroupRepository.findByGroupCode(group.get().getCode());
            if(userGroups.isEmpty()){
                group.get().setUserGroups(null);
            }
            else {
                group.get().setUserGroups(userGroups);
            }
        }
        groupRepository.save(group.get());
        return group.get();
    }

    @Override
    public Group getInforByCode(String code) {

        return groupRepository.findByCode(code).get();
    }
}
