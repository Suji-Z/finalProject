package com.project.tour.repository;

import com.project.tour.domain.Member;
import com.project.tour.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReviewRepository extends JpaRepository <Review,Long> {

    Page<Review> findAll(Pageable pageable);

    //마이페이지에 출력할 멤버의 리뷰 리스트(댓글이랑 같이 띄울때 페이징처리 할건지 생각)
    List<Review> findByAuthor_Id(Long id);






}
