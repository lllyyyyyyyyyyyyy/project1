package com.vss.social_webapp.service.impliment;

import com.vss.social_webapp.model.RelationshipFb;
import com.vss.social_webapp.repository.RelationshipRepository;
import com.vss.social_webapp.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    RelationshipRepository relationshipRepository;

    @Override
    public void addFriend(String userEmail1, String userEmail2) {

        List<RelationshipFb> relationshipFbList = relationshipRepository.findByPersonOneEmail(userEmail1);
        if(!relationshipFbList.isEmpty()){
            for(RelationshipFb relationshipFb:relationshipFbList){
                if(relationshipFb.getPersonTwoEmail().equals(userEmail2)){
                    if(relationshipFb.isActive()){
                        relationshipFb.setActive(false);
                    }
                    else relationshipFb.setActive(true);
                    relationshipRepository.save(relationshipFb);
                }
                else addNewFriend(userEmail1, userEmail2);
            }
        }
        else addNewFriend(userEmail1, userEmail2);

    }

    @Override
    public List<RelationshipFb> findByPersonOneEmail(String userEmail1) {

        List<RelationshipFb> relationshipFbList = relationshipRepository.findByPersonOneEmail(userEmail1);
        return relationshipFbList;
    }

    @Override
    public List<RelationshipFb> findByPersonTwoEmail(String userEmail2) {

        List<RelationshipFb> relationshipFbList = relationshipRepository.findByPersonTwoEmail(userEmail2);
        return relationshipFbList;
    }

    private void addNewFriend(String userEmail1, String userEmail2){
        RelationshipFb relationship  = new RelationshipFb();
        relationship.setPersonOneEmail(userEmail1);
        relationship.setPersonTwoEmail(userEmail2);
        relationship.setActive(true);
        relationshipRepository.save(relationship);
    }
}
