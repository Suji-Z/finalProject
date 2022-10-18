package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 패키지 상세페이지에서 예약하기 눌럿을때 넘겨줄 데이터
 * */
public class BookingDTO {

    private String departure;
    private String arrival;

    private Integer bookingacount;
    private Integer bookingbcount;
    private Integer bookingccount;

}
