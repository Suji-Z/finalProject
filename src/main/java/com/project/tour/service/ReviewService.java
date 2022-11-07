package com.project.tour.service;


import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.repository.BookingRepository;
import com.project.tour.repository.MemberRepository;
import com.project.tour.repository.ReviewLikeRepository;
import com.project.tour.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ReviewService {

    private  final ReviewRepository reviewRepository;

    private final BookingRepository bookingRepository;

    private final MemberRepository memberRepository;

    private final ReviewLikeRepository reviewLikeRepository;

    public Review create(String subject, String content, String reviewImage, Double score, Member author, Package reviewPackage){
        Review review = new Review();

        review.setSubject(subject);
        review.setContent(content);
        review.setScore(score);
        review.setCreated(LocalDateTime.now());
        review.setReviewImage(reviewImage);
        review.setAuthor(author);
        review.setReviewPackages(reviewPackage);

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
        sorts.add(Sort.Order.desc("id"));

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 :
                        pageable.getPageNumber() - 1,
                        pageable.getPageSize(),Sort.by(sorts));

        return reviewRepository.findAll(pageable);


    }

    public void vote(Review review, Member member){

        ReviewLike reviewLike = new ReviewLike();

        reviewLike.setReview(review);
        reviewLike.setMember(member);

        reviewLikeRepository.save(reviewLike);

    }

    public void delete(Review review){

        reviewRepository.delete(review);
    }


    public void deleteLike(Long id1, Long id2){

        Optional<ReviewLike> reviewId = reviewLikeRepository.findByMember_IdAndReview_Id(id1, id2);

        reviewLikeRepository.delete(reviewId.get());
    }

    public void update(Review review, String subject, String content,
                       String reviewImage, Double score, Package reviewPackage){

        review.setSubject(subject);
        review.setContent(content);
        review.setReviewImage(reviewImage);
        review.setScore(score);
        review.setReviewPackages(reviewPackage);

        reviewRepository.save(review);


    }

    public List<UserBooking> getBookingReview(Long id, int status){

        List<UserBooking> op = bookingRepository.findByMember_IdAndBookingStatus(id,status);

        return op;

    }

    //리뷰라이크 가져오기
    public int getReviewLike(Long id1,Long id2){

        Optional<ReviewLike> op2 = reviewLikeRepository.findByMember_IdAndReview_Id(id1,id2);

        if(op2.isPresent())
            return 1;
        else
            return 0;


    }

    //조회수
    public void updateHitCount(int num, Long id){

        Optional<Review> op = reviewRepository.findById(id);
        op.get().setHitCount(num);

        reviewRepository.save(op.get());

    }

    public Page<Review> getJejuList(String location, Pageable pageable){

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("created"));

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 :
                        pageable.getPageNumber() - 1,
                pageable.getPageSize(),Sort.by(sorts));

        return reviewRepository.findByReviewPackages_Location1(location, pageable);


    }

    public Page<Review> getAbroadList(List<String> location, Pageable pageable){

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("created"));

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 :
                        pageable.getPageNumber() - 1,
                pageable.getPageSize(),Sort.by(sorts));

        return reviewRepository.findByReviewPackages_Location1In(location, pageable);

    }





}
