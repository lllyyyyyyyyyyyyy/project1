package com.vss.social_webapp.repository;

import com.vss.social_webapp.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Long> {
    List<Picture> findAllByPostCode(String postCode);
}
