package com.project.tour.service;


import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Member;
import com.project.tour.domain.Review;
import com.project.tour.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private  final ReviewRepository reviewRepository;

    public Review create(String subject, String content,String reviewImage, Double score, Member author ){
        Review review = new Review();

        review.setSubject(subject);
        review.setContent(content);
        review.setScore(score);
        review.setCreated(LocalDateTime.now());
        review.setReviewImage(reviewImage);
        review.setAuthor(author);

        return reviewRepository.save(review);
    }

    public Review getReview(Long id){

        Optional<Review> op = reviewRepository.findById(id);

        if(op.isPresent())
            return op.get();
        else
            throw new DataNotFoundException("Review Not Found");
    }

    public Review getReplyReview(Long id){

        Optional<Review> op2 = reviewRepository.findById(id);

        if(op2.isPresent())
            return op2.get();
        else
            throw new DataNotFoundException("Review Not Found");

    }

    public Page<Review> getList(Pageable pageable){

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("created"));

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 :
                        pageable.getPageNumber() - 1,
                        pageable.getPageSize(),Sort.by(sorts));

        return reviewRepository.findAll(pageable);


    }

    public void vote(Review review, Member member){

        if(review.getVoter().isEmpty()){
            review.getVoter().add(member);
            reviewRepository.save(review);
        }else{
            review.setVoter(null);
            reviewRepository.save(review);

        }

    }

    public void delete(Review review){

        reviewRepository.delete(review);
    }

    public void update(Review review, String subject, String content,
                       String reviewImage, Double score){

        review.setSubject(subject);
        review.setContent(content);
        review.setReviewImage(reviewImage);
        review.setScore(score);

        reviewRepository.save(review);


    }


}
