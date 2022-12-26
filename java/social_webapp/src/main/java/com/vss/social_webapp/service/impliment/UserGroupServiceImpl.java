package com.vss.social_webapp.service.impliment;

import com.vss.social_webapp.model.Group;
import com.vss.social_webapp.model.UserGroup;
import com.vss.social_webapp.repository.GroupRepository;
import com.vss.social_webapp.repository.UserGroupRepository;
import com.vss.social_webapp.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    UserGroupRepository userGroupRepository;
    @Autowired
    GroupRepository groupRepository;
    @Override
    public void saveUserGroup(UserGroup userGroup) {
        userGroupRepository.save(userGroup);
    }

    @Override
    public Optional<UserGroup> findByUserEmail(String userEmail) {
        Optional<UserGroup> userGroup = userGroupRepository.findByUserEmail(userEmail);
        return userGroup;
    }

    @Override
    public List<UserGroup> getAllGroup() {
        return userGroupRepository.findAll();
    }

    @Override
    public List<UserGroup> getAllInfoGroup() {
        List<UserGroup> userGroups = userGroupRepository.findAll();
        for(UserGroup userGroup:userGroups){
            Optional<Group> group = groupRepository.findByCode(userGroup.getGroupCode());
            if(group.isPresent()){
                userGroup.setGroup(group.get());
            }
            else {
                userGroup.setGroup(null);
            }
        }
        return userGroups;
    }

    @Override
    public List<UserGroup> findByGroupCode(String groupCode) {

        List<UserGroup> userGroups = userGroupRepository.findByGroupCode(groupCode);
        return  userGroups;
    }

    @Override
    public boolean checkJoinGroup(String userEmail, String groupCode) {

        List<UserGroup> userGroups = userGroupRepository.findByGroupCode(groupCode);
        if(userGroups.isEmpty()){
            return false;
        }
        else {
            for(UserGroup userGroup:userGroups){
                Optional<UserGroup> userGroup1 = userGroupRepository.findByUserEmail(userGroup.getUserEmail());
                if(userGroup1.isPresent()){
                    return true;
                }
                else return false;
            }
        }
        return false;
    }

}
