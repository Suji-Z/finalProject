package com.project.tour.repository;

import com.project.tour.domain.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike,Long> {

    Optional<ReplyLike> findByMember_IdAndReview_Id(Long id1,Long id2);
}
