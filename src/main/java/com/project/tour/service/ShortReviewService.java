package com.project.tour.service;

import com.project.tour.domain.Member;
import com.project.tour.domain.Package;
import com.project.tour.domain.ShortReview;
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

@RequiredArgsConstructor
@Service
public class ShortReviewService {

    private final ShortReviewRepository shortReviewRepository;



    public ShortReview create(String content, Double score, Member userName, Package packages){

        ShortReview shortReview = new ShortReview();

        shortReview.setContent(content);
        shortReview.setScore(score);
        shortReview.setCreated(LocalDateTime.now());
        shortReview.setPackages(packages);
        shortReview.setUserName(userName);

        return shortReviewRepository.save(shortReview);
    }


    public Page<ShortReview> getShortReview(Long id, Pageable pageable){

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable= PageRequest.of(
                pageable.getPageNumber()<=0?0:
                        pageable.getPageNumber()-1,
                pageable.getPageSize(),Sort.by(sorts));

        return shortReviewRepository.findByPackages_Id(id,pageable);

    }




}
