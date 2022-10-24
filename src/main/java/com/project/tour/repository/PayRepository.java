package com.project.tour.repository;

import com.project.tour.domain.Member;
import com.project.tour.domain.Pay;
import com.project.tour.domain.UserBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayRepository extends JpaRepository<Pay,Long> {

    Optional<Pay> findById(Long id);

    Boolean existsByMember_id(Long id);

    Optional<Pay> findByMemberAndPayDate(Member member, LocalDateTime payDate);

    @Query(value = "SELECT max(id) FROM Pay")
    public Long maxPayNum();


    //관리자 판매 내역 페이징
    Page<Pay> findAll(Pageable pageable);

    List<Pay> findAll();


    //회원이 결제완료한 내역(사용포인트 출력)
    List<Pay> findByMember_Id(Long id);


}
