package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Member;
import com.project.tour.domain.Review;
import com.project.tour.domain.Review_reply;
import com.project.tour.repository.ReviewReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ReviewReplyService {

    private final ReviewReplyRepository reviewReplyRepository;

    public Review_reply create(Review review, String content,Member member){

        Review_reply review_reply = new Review_reply();

        review_reply.setContent(content);
        review_reply.setCreated(LocalDateTime.now());
        review_reply.setReviewNum(review);
        review_reply.setAuthor(member);

        reviewReplyRepository.save(review_reply);

        return review_reply;


    }

    public Review_reply getReply(Long id){

        Optional<Review_reply> reviewReply = reviewReplyRepository.findById(id);

        if(reviewReply.isPresent()){
            return reviewReply.get();
        }else {
            throw new DataNotFoundException("댓글이 없습니다");
        }
    }

    public void update(Review_reply review_reply, String content){

        review_reply.setContent(content);

        reviewReplyRepository.save(review_reply);

    }

    public void delete(Review_reply review_reply){
        reviewReplyRepository.delete(review_reply);
    }

    public void vote(Review_reply review_reply, Member member){

        if(review_reply.getVoter().isEmpty()) {
            review_reply.getVoter().add(member);
            reviewReplyRepository.save(review_reply);

        }else{
            review_reply.setVoter(null);
            reviewReplyRepository.save(review_reply);
        }

    }






}
