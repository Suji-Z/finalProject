package com.project.tour.kakao.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayApprove {

    //결제 승인 요청

    private String aid;
    private String tid;
    private String cid; //가맹점코드 = TC0ONETIME
    private String sid;
    private String partner_order_id; //가맹점 주문번호 = bookingNum
    private String partner_user_id; //가맹점 회원 아이디 = member.email or member.id
    private String payment_method_type;
    private String item_name;   //패키지 명 = packageName
    private String item_code;   //패키지 넘버 = packageNum 필수가 아닌듯?
    private int quantity;   //상품수량 = 1
    private String created_at;
    private String approved_at;
    private String payload;
    private KakaoPayAmount kakaoPayAmount;  //상품총액 ?

}
