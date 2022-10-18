package com.project.tour.kakao.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayReady {

    //결제 요청

    private String tid; //결제 고유 번호 자동부여
    private String next_redirect_pc_url; //이동시킬 url
    private String partner_order_id;    //bookingNum


}
