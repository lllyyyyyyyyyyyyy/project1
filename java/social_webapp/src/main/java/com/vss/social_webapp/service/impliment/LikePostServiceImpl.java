package com.vss.social_webapp.service.impliment;

import com.vss.social_webapp.model.LikePost;
import com.vss.social_webapp.repository.LikePostRepository;
import com.vss.social_webapp.service.LikePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikePostServiceImpl implements LikePostService {

    @Autowired
    LikePostRepository likePostRepository;

    @Override
    public void saveLikePost(LikePost likePost) {
        likePostRepository.save(likePost);
    }

    @Override
    public boolean checkLikePost(String userName) {

        Optional<LikePost> likePost = likePostRepository.findByUserName(userName);
        if(likePost.isPresent()){
            return true;
        }
        else return false;
    }

    @Override
    public List<LikePost> findByPostCode(String postCode) {
        List<LikePost> likePosts = likePostRepository.findByPostCode(postCode);
        return likePosts;
    }

    @Override
    public LikePost findByUserName(String userName) {
        Optional<LikePost> likePost = Optional.of(new LikePost());
        try {
            likePost = likePostRepository.findByUserName(userName);
        }catch (Exception e){
            e.printStackTrace();
        }

        return likePost.get();
    }

    @Override
    public boolean checkIfUserLikedPost(String userName, String postCode) {
        List<LikePost> likePosts = likePostRepository.findByPostCode(postCode);
        if(!likePosts.isEmpty()){
            for(LikePost likePost:likePosts) {
                if (likePost.getUserName().equals(userName)) {
                    return false;
                }
            }
        }
        return true;
//        Optional<LikePost> likePost = likePostRepository.findByUserName(userName);
//        if(likePost.isPresent()) return false;
//        else return true;
    }

    @Override
    public List<LikePost> getAllLikePost() {
        List<LikePost> likePosts = likePostRepository.findAll();
        return likePosts;
    }

    @Override
    public boolean checkLikeComment(String userName, String postCode, String commentCode) {

        Optional<LikePost> likePost = likePostRepository.findByPostCodeAndUserNameAndCommentCode(postCode, userName, commentCode);

        return !likePost.isPresent();

    }

    @Override
    public Optional<LikePost> findByUserNameAndPostCodeAndCommentCode(String userName, String postCode, String commentCode) {
        Optional<LikePost> likePost = likePostRepository.findByPostCodeAndUserNameAndCommentCode(postCode, userName, commentCode);
        return likePost;
    }

    @Override
    public List<LikePost> findByUserNameAndPostCode(String userName, String postCode) {
        List<LikePost> likePosts = likePostRepository.findByPostCodeAndUserName(postCode, userName);
        return likePosts;
    }
}
