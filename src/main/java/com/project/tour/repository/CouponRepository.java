package com.project.tour.repository;

import com.project.tour.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,String> {
    //Optional<Coupon> findById(String[] id);
//    @Query("SELECT c FROM Coupon c WHERE c.id IN (:couponNum)")
//    List<Coupon> findAllById(@Param("couponNum") List<String> coupons);

    List<Coupon> findByIdIn(List<String> coupons); //list안의 스트링을 or가지고 있는 애들 다 뽑아내라
    Optional<Coupon> findById(String id);
}


