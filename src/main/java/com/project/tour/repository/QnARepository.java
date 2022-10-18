package com.project.tour.repository;

import com.project.tour.domain.QnA;
import com.project.tour.domain.QnA_Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnARepository extends JpaRepository<QnA, Long> {

    Page<QnA> findAll(Pageable pageable);

    //마이페이지에 출력할 멤버의 QnA 리스트
    Page<QnA> findByMember_Id(Long id, Pageable pageable);

}
