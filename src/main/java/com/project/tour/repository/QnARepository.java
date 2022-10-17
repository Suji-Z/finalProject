package com.project.tour.repository;

import com.project.tour.domain.QnA;
import com.project.tour.domain.QnA_Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QnARepository extends JpaRepository<QnA, Long> {

    Page<QnA> findAll(Pageable pageable);

}
