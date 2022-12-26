package com.vss.social_webapp.repository;

import com.vss.social_webapp.model.RelationshipFb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipRepository extends JpaRepository<RelationshipFb,Long> {
    List<RelationshipFb> findByPersonOneEmail(String userEmail1);

    List<RelationshipFb> findByPersonTwoEmail(String userEmail2);
}
