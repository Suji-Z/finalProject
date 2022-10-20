package com.project.tour.repository;

import com.project.tour.domain.Member;
import com.project.tour.domain.Review;
import com.project.tour.domain.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike,Long> {
    Optional <ReviewLike> findByMember_IdAndReview_Id(Long id1,Long id2);



}
