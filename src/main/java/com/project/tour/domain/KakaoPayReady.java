package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class KakaoPayReady {

    //결제 요청

    private String tid;
    private String next_redirect_pc_url;
    private String partner_order_id;


}
