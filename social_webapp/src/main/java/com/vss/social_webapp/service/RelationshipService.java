package com.vss.social_webapp.service;

import com.vss.social_webapp.model.RelationshipFb;

import java.util.List;

public interface RelationshipService {
    void addFriend(String userEmail1, String userEmail2);

    List<RelationshipFb> findByPersonOneEmail(String userEmail1);

    List<RelationshipFb> findByPersonTwoEmail(String userEmail2);
}
