package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter @Setter
@SequenceGenerator(
        name = "BOOKING_SEQ_GENERATOR",
        sequenceName = "BOOKING_SEQ", // 매핑할 데이터베이스 시퀀스 이름
        initialValue = 19012032,
        allocationSize = 51
)
public class UserBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "BOOKING_SEQ_GENERATOR")
    @Column(name = "bookingNum")
    private Long id;

    private String departure;
    private String arrival;

    private int travelPeriod;

    private int aCount;
    private int bCount;
    private int cCount;

    private String travelerName;
    private String travelerTel;
    private String travelerBirth;

    @Column(columnDefinition = "TEXT",length = 500)
    private String request;

    private LocalDateTime bookingDate;

    private int bookingTotalPrice;

    private int bookingStatus; // 0:예약확인중 1:결제대기중 2:결제완료

    private int bookingTotalCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "packageNum")
    private Package apackage;


}