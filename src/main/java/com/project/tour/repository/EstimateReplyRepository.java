package com.project.tour.repository;

import com.project.tour.domain.EstimateInquiry;
import com.project.tour.domain.EstimateReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstimateReplyRepository extends JpaRepository<EstimateReply, Long> {

    EstimateReply findByEstimateInquiry(EstimateInquiry inquiry);
}
