package com.project.tour.domain;

import javax.persistence.*;

public class Coupon {

    //쿠폰번호, 쿠폰명, 할인율

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponNum")
    private Long id;

    private String couponName;

    private double couponRate;

    @ManyToOne
    private Member coupons;

}
