package com.project.tour.repository;

import com.project.tour.domain.Member;
import com.project.tour.domain.Notice;
import com.project.tour.domain.NoticeRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRecommendRepository extends JpaRepository<NoticeRecommend,Long> {

    Optional<NoticeRecommend> findByMemberAndNotice(Member member, Notice notice);
}
