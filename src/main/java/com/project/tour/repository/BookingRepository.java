package com.project.tour.repository;

import com.project.tour.domain.UserBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<UserBooking, Long> {

    Page<UserBooking> findAll(Pageable pageable);

}
