package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingPriceDTO {
    //쿠폰적용시 사용해야할 가격 정보

    private int bookingTotalPrice;
    private int couponDiscountPrice;

}
