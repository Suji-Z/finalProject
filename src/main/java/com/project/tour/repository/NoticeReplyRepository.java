package com.project.tour.repository;

import com.project.tour.domain.Notice;
import com.project.tour.domain.NoticeReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeReplyRepository extends JpaRepository<NoticeReply,Long> {

    List<NoticeReply> findAllByNotice(Notice notice);

    //마이페이지에 적립포인트 계산(댓글달면 500포인트 적립)
    List<NoticeReply> findByMember_Id(Long id);

    Optional<NoticeReply> findByNotice_IdAndMember_Id(Long noticeNum, Long memberNum);


}
