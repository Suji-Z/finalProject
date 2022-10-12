package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class UserBooking {

    @Id
    @Column(name = "bookingNum")
    private Long id;

    private LocalDateTime departureDate;

    private LocalDateTime arrivalDate;

    private int travelPeriod;

    private int a_travelerCount;

    private int b_travelerCount;

    private int c_travelerCount;

    private String request;

    private LocalDateTime bookingDate;

    private int bookingTotalPrice;

    private int bookingStatus;

    private int bookingTotalCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "packageNum")
    private Package aPackage;


}