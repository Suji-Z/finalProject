package com.project.tour.repository;

import com.project.tour.domain.ShortReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShortReviewRepository extends JpaRepository<ShortReview,Long> {



    //패키지와 관련된 리뷰만 출력할 리스트
    List<ShortReview> findByPackages_Id(Long id);

    Optional<ShortReview> findByIdAndPackages_Id(Long id, Long packageNum);
}
