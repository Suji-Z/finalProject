package com.project.tour.kakao.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayAmount {

    //결제 내역 호출

    private int total;
    private int tax_free;
    private int vat;
    private int point;
    private int discount;

}
