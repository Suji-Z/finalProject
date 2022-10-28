package com.project.tour.repository;

import com.project.tour.domain.Review_reply;
import jdk.nashorn.internal.ir.Optimistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewReplyRepository extends JpaRepository<Review_reply, Long> {

    Optional<Review_reply> findByIdAndAuthor_Id(Long id1, Long id2);


}
