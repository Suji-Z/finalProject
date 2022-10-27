package com.project.tour.repository;

import com.project.tour.domain.Package;
import com.project.tour.domain.VoiceCus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoiceCusRepository extends JpaRepository<VoiceCus, Integer> {

    VoiceCus findBySubject(String subject);
    VoiceCus findBySubjectAndContent(String subject,String content);
    //Page<VoiceCus> findBySubjectContaining(String subject, Pageable pageable);

    Page<VoiceCus> findByTypes(String type,Pageable pageable);


    Page<VoiceCus> findAll(Pageable pageable);

    //마이페이지에 출력할 멤버의 고객의소리 리스트
    Page<VoiceCus> findByAuthor_Id(Long id, Pageable pageable);
}
