package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserBookingForm {

    private String departure;
    private String arrival;

    private int travelPeriod;

    private int aCount;

    private int bCount;

    private int cCount;

    private String request;

    private LocalDateTime bookingDate;

    private int bookingTotalPrice;

    private int bookingStatus;  // 0: 예약확인중 1:결제대기중 2:결제완료

    private int bookingTotalCount;


}
