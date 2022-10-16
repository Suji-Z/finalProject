package com.project.tour.domain;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

public class Pay {

    @Id
    @Column(name = "payNum")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToOne
    @JoinColumn(name = "id") //여기 private는 왜 해주지?
    private UserBooking userBooking;

    @ManyToOne //한사람당 여러개의 결제내역 가능
    @JoinColumn(name = "member_id")
    Member member;

    String payment;
    int totalPrice;
    String payDate;
    int totalCount;

}
