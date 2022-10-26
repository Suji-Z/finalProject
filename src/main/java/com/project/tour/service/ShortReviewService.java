package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.repository.BookingRepository;
import com.project.tour.repository.PackageRepository;
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

    private final PackageRepository packageRepository;




    public ShortReview create(String content, Double score, Member member, Package packages){

        ShortReview shortReview = new ShortReview();

        shortReview.setContent(content);
        shortReview.setScore(score);
        shortReview.setCreated(LocalDateTime.now());
        shortReview.setPackages(packages);
        shortReview.setUserName(member);

        return shortReviewRepository.save(shortReview);
    }


    public List<UserBooking> getBookingShortReview(Long id, int status, Package aPackage){

        List<UserBooking> op = bookingRepository.findByMember_IdAndBookingStatusAndApackage(id,status,aPackage);

        return op;

    }


    public List<ShortReview> getshortReviewList(Long packageNum){

        Optional<Package> packages = packageRepository.findById(packageNum);

        return shortReviewRepository.findAllByPackages(packages.get());

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
