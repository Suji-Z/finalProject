package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Member;
import com.project.tour.domain.ReplyLike;
import com.project.tour.domain.Review;
import com.project.tour.domain.Review_reply;
import com.project.tour.repository.ReplyLikeRepository;
import com.project.tour.repository.ReviewReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ReviewReplyService {

    private final ReviewReplyRepository reviewReplyRepository;
    private final ReplyLikeRepository replyLikeRepository;


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

    public void deleteReplyLike(Long id1, Long id2){
        Optional<ReplyLike> op2 = replyLikeRepository.findByMember_IdAndReview_Id(id1,id2);

        replyLikeRepository.delete(op2.get());



    }


    public Review_reply getReplyStatus(Long id1,Long id2){

        Optional<Review_reply> reviewReply = reviewReplyRepository.findByIdAndAuthor_Id(id1,id2);

        if(reviewReply.isPresent()){
            return reviewReply.get();
        }else {
            throw new DataNotFoundException("멤버댓글이 없습니다");
        }
    }

//    public void vote(Review_reply review_reply, Member member , Review review){
//
//        ReplyLike replyLike = new ReplyLike();
//
//        replyLike.setReply(review_reply);
//        replyLike.setMember(member);
//        replyLike.setReview(review);
//
//        replyLikeRepository.save(replyLike);
//
//
//    }

//    public boolean getReplyLike(Long id1,Long id2){
//        Optional<ReplyLike> op2 = replyLikeRepository.findByMember_IdAndReview_Id(id1,id2);
//
//        if(op2.isPresent())
//            return true;
//        else
//            return false;
//
//    }













}
