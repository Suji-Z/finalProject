package com.project.tour.repository;

import com.project.tour.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Page<Notice> findAll(Pageable pageable);
    Page<Notice> findByCategory(String category, Pageable pageable);
    Page<Notice> findBySubjectContainingIgnoreCase(String searchKeyword, Pageable pageable);
    Page<Notice> findByCategoryAndSubjectContainingIgnoreCase(String category, String searchKeyword, Pageable pageable);
    Optional<Notice> findById(Long id);

    //public Long CountBy();

    @Query(value = "select count(*) from notice where pin=true", nativeQuery = true)
    int countPin();

    //마이페이지 - 멤버가 댓글 쓴 Notice list 출력
    List<Notice> findByIdIn(List<Long> noticeNum);






}
