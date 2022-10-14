package com.project.tour.service;

import com.project.tour.domain.Review;
import com.project.tour.domain.Review_reply;
import com.project.tour.repository.ReviewReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReviewReplyService {

    private final ReviewReplyRepository reviewReplyRepository;

    public Review_reply create(Review review, String content){

        Review_reply review_reply = new Review_reply();

        review_reply.setContent(content);
        review_reply.setCreated(LocalDateTime.now());
        review_reply.setReviewNum(review);

        reviewReplyRepository.save(review_reply);

        return review_reply;

    }






}
