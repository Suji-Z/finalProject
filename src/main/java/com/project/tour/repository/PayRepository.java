package com.project.tour.repository;

import com.project.tour.domain.Member;
import com.project.tour.domain.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayRepository extends JpaRepository<Pay,Long> {

    Optional<Pay> findById(Long id);
    Optional<Pay> findByMemberAndPayDate(Member member, String payDate);




}
