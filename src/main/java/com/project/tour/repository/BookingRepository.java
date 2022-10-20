package com.project.tour.repository;

import com.project.tour.domain.Member;
import com.project.tour.domain.QnA;
import com.project.tour.domain.UserBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<UserBooking, Long> {

    Page<UserBooking> findAll(Pageable pageable);
    Optional<UserBooking> findById(Long id);

    //사용자 정보와 예약날짜를 가지고 bookingNum 가져오기
    Optional<UserBooking> findByMemberAndBookingDate(Member member, String bookingDate);

    //예약번호로 예약정보 가져오기


    //마이페이지에 출력할 멤버의 예약 리스트
    Page<UserBooking> findByMember_Id(Long id, Pageable pageable);

    //리뷰페이지에서 사용할 결제완료된 예약정보 리스트 가져오기
    List<UserBooking> findByMember_IdAndBookingStatus(Long id, int status);

}
