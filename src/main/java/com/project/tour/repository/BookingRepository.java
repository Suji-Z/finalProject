package com.project.tour.repository;

import com.project.tour.domain.Member;
import com.project.tour.domain.UserBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<UserBooking, Long> {

    Page<UserBooking> findAll(Pageable pageable);
    Optional<UserBooking> findById(Long id);

    //사용자 정보와 예약날짜를 가지고 bookingNum 가져오기
    Optional<UserBooking> findByMemberAndBookingDate(Member member, String bookingDate);

}
