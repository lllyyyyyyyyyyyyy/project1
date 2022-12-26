package com.vss.social_webapp.service.impliment;

import com.vss.social_webapp.model.Picture;
import com.vss.social_webapp.repository.PictureRepository;
import com.vss.social_webapp.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    PictureRepository pictureRepository;

    @Override
    public void savePicture(Picture picture) {
        pictureRepository.save(picture);
    }
}
