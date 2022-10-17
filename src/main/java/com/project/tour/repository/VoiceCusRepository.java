package com.project.tour.repository;

import com.project.tour.domain.VoiceCus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoiceCusRepository extends JpaRepository<VoiceCus, Integer> {

    VoiceCus findBySubject(String subject);
    VoiceCus findBySubjectAndContent(String subject,String content);
    List<VoiceCus> findBySubjectLike(String subject);

    VoiceCus findByTypes(String types);

    Page<VoiceCus> findAll(Pageable pageable);
}
