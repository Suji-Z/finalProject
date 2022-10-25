package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Pay {

    @Id
    @Column(name = "payNum")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToOne
    @JoinColumn(name = "bookingNum") //여기 private는 왜 해주지?
    private UserBooking userBooking;

    @ManyToOne //한사람당 여러개의 결제내역 가능
    @JoinColumn(name = "member_id")
    Member member;

    String payMethod;

    int payTotalPrice; //적립금 사용한 금액

    LocalDateTime payDate;

    String payInfo;

    int usedPoint;



}
