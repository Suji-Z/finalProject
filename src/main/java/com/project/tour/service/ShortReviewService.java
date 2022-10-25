package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.repository.BookingRepository;
import com.project.tour.repository.ShortReviewRepository;
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
public class ShortReviewService {

    private final ShortReviewRepository shortReviewRepository;

    private final BookingRepository bookingRepository;




    public ShortReview create(String content, Double score, Member userName, Package packages){

        ShortReview shortReview = new ShortReview();

        shortReview.setContent(content);
        shortReview.setScore(score);
        shortReview.setCreated(LocalDateTime.now());
        shortReview.setPackages(packages);
        shortReview.setUserName(userName);

        return shortReviewRepository.save(shortReview);
    }


//    public List<UserBooking> getBookingReview(Long id, int status){
//
//        List<UserBooking> op = bookingRepository.findByMember_IdAndBookingStatus(id,status);
//
//        return op;
//
//    }


    public ShortReview getshortReview(Long id){

        Optional<ShortReview> op = shortReviewRepository.findById(id);

        if(op.isPresent())
            return op.get();
        else
            throw new DataNotFoundException("리뷰가 없습니다");

    }


    public ShortReview getshortReviewId(Long id,Long packageNum){

        Optional<ShortReview> op = shortReviewRepository.findByIdAndPackages_Id(id,packageNum);

        if(op.isPresent())
            return op.get();
        else
            throw new DataNotFoundException("리뷰가 없습니다");

    }



    public void update(ShortReview shortReview, String content,
                       Double score, Package packages){

        shortReview.setContent(content);
        shortReview.setScore(score);
        shortReview.setPackages(packages);

        shortReviewRepository.save(shortReview);


    }






}
