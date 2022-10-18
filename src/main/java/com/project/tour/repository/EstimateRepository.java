package com.project.tour.repository;

import com.project.tour.domain.EstimateInquiry;
import com.project.tour.domain.QnA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimateRepository extends JpaRepository<EstimateInquiry, Long> {


    /**Pageable을 입력받아 Page<EstimateInquiry> 타입의 객체로 리턴*/
    Page<EstimateInquiry> findAll(Pageable pageable);

    //마이페이지에 출력할 멤버의 견적문의 리스트
    Page<EstimateInquiry> findByEmail(String email, Pageable pageable);


}
