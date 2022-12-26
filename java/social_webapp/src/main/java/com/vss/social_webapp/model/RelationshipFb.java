package com.vss.social_webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "relationship")
public class RelationshipFb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "person_one_email")
    private String personOneEmail;

    @Column(name = "person_two_email" )
    private String personTwoEmail;

    @Column(name = "is_active")
    private boolean active;

}
