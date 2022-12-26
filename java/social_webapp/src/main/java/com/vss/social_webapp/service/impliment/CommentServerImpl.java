package com.vss.social_webapp.service.impliment;

import com.vss.social_webapp.dtos.CommentDTO;
import com.vss.social_webapp.model.CommentPost;
import com.vss.social_webapp.repository.CommentRepository;
import com.vss.social_webapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServerImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Override
    public void saveComment(CommentPost commentPost) {
        commentRepository.save(commentPost);
    }

    @Override
    public Optional<CommentPost> findByCommentCode(String commentCode) {
        Optional<CommentPost> commentPost = commentRepository.findByCode(commentCode);
        if (commentPost.isPresent()) {
            if (commentPost.get().getParentCommentCode() == null) {
                return commentPost;
            }
        }
        return Optional.empty();
    }

    @Override
    public List<CommentDTO> findCommentChildByPostCode(String postCode) {

        List<CommentPost> commentPosts = commentRepository.findByPostCode(postCode);
        List<CommentPost> commentPosts2 = commentRepository.findAll();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        if(!commentPosts.isEmpty()){
            for(CommentPost commentPost:commentPosts){
                for(CommentPost commentPost1: commentPosts2){
                    if(commentPost1.getParentCommentCode() != null){
                        if(commentPost.getCode().equals(commentPost1.getParentCommentCode())){
                            CommentDTO commentDTO = new CommentDTO();
                            commentDTO.setId(commentPost1.getId());
                            commentDTO.setContent(commentPost1.getContent());
                            commentDTO.setCreateAt(commentPost1.getCreateAt());
                            commentDTO.setCreateBy(commentPost1.getCreateBy());
                            commentDTO.setUpdateAt(commentPost1.getUpdateAt());
                            commentDTO.setUpdateBy(commentPost1.getUpdateBy());
                            commentDTO.setCode(commentPost1.getCode());
                            commentDTO.setUserEmail(commentPost1.getUserEmail());
                            commentDTO.setPostCode(commentPost1.getPostCode());
                            commentDTO.setPicture(commentPost1.getPicture());
                            commentDTO.setPictureComment(commentPost1.getPictureComment());
                            commentDTO.setParentCommentCode(commentPost1.getParentCommentCode());
                            commentDTO.setSumLikeComment(commentPost1.getSumLikeComment());
                            commentDTOS.add(commentDTO);
                        }
                    }
                }
            }
        }
        return commentDTOS;
    }
}


//        List<CommentPost> commentPosts = new ArrayList<>();
//        List<CommentPost> commentPostList = commentRepository.findByPostCode(postCode);
//        for (int i = 0; i < commentPostList.size(); i++) {
//
//            CommentPost commentPost = commentPostList.get(i);
//
//            commentPosts.add(commentPost);
//            List<CommentPost> commentPostList1 = commentRepository.findAll();
//
//            for (int j = 0; j < commentPostList1.size(); j++) {
//                CommentPost commentPost1 = commentPostList1.get(j);
//                if (commentPost1.getParentCommentCode() != null) {
//                    if (commentPost1.getParentCommentCode().equals(commentPost.getCode())) {
//
//                        commentPost.setCommentPosts(commentPostList1);
//
//                    }
//                }
//            }
//
//
//        }
//        return commentPostList;
//    }
//}
//






//    }


//}
