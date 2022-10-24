package com.project.tour.repository;

import com.project.tour.domain.Notice;
import com.project.tour.domain.NoticeReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeReplyRepository extends JpaRepository<NoticeReply,Long> {

    List<NoticeReply> findAllByNotice(Notice notice);


}
