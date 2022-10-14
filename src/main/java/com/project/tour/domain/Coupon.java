package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Coupon {

    //쿠폰번호, 쿠폰명, 할인율

    @Id
    @Column(name = "couponNum")
    private String id;

    private String couponName;

    private double couponRate;

}
