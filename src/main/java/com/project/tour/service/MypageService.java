package com.project.tour.service;

import com.project.tour.domain.EstimateInquiry;
import com.project.tour.domain.Review;
import com.project.tour.repository.EstimateRepository;
import com.project.tour.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MypageService {

    private final ReviewRepository reviewRepository;

    private final EstimateRepository estimateRepository;

    public List<Review> getMypageReview(Long id){

        List<Review> op = reviewRepository.findByAuthor_Id(id);

        return op;


    }

    public List<EstimateInquiry> getMypageEstimate(String email){
        List<EstimateInquiry> op2 = estimateRepository.findByEmail(email);

        return op2;
    }


}
