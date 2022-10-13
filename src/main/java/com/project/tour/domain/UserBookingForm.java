package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserBookingForm {

    private LocalDateTime departureDate;

    private int travelPeriod;

    private int a_travelerCount;

    private int b_travelerCount;

    private int c_travelerCount;

    private String request;

    private LocalDateTime bookingDate;

    private int bookingTotalPrice;

    @ColumnDefault(value = "0") // 0: 예약확인중 1:결제대기중 2:결제완료
    private int bookingStatus;

    private int bookingTotalCount;


}
