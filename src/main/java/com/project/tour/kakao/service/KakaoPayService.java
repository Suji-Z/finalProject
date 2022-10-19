package com.project.tour.kakao.service;

import com.project.tour.domain.Member;
import com.project.tour.domain.UserBooking;
import com.project.tour.kakao.vo.KakaoPayApprove;
import com.project.tour.kakao.vo.KakaoPayReady;
import com.project.tour.repository.BookingRepository;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Log
@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoPayService {

        private final MemberRepository memberRepository;
        private final BookingRepository bookingRepository;

    public KakaoPayReady payReady(int totalAmount, Member member) {

        //주문자 정보랑 예약정보 필요 > 매개변수로 member, bookingNum, 총 결제 금액 을 받아야할듯?
        long bookingNum = 1; //테스트용 코드
        Optional<UserBooking> userBooking = bookingRepository.findById(bookingNum);

        // 카카오가 요구한 결제요청request값을 담아줍니다.
        //cid,partner_order_id,partner_user_id,item_name,quantity
        //total_amount,tax_free_amount,approval_url,cancel_url,fail_url

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("cid", "TC0ONETIME");    //가맹점 번호
        parameters.add("partner_order_id", String.valueOf(bookingNum)); //주문번호.그냥 예약번호 사용하자
        parameters.add("partner_user_id", member.getEmail());
        parameters.add("item_name", userBooking.get().getAPackage().getPackageName());
        parameters.add("quantity", "1"); //우린 패키지 1개만 결제가능이니까
        parameters.add("total_amount", String.valueOf(totalAmount));
        parameters.add("tax_free_amount", "0");
        parameters.add("approval_url", "http://localhost:8088/pay/kakaopay/completed"); // 결제승인시 넘어갈 url
        parameters.add("cancel_url", "http://localhost:8088/pay/kakaopay/cancel"); // 결제취소시 넘어갈 url
        parameters.add("fail_url", "http://localhost:8088/pay/kakaopay/fail"); // 결제 실패시 넘어갈 url

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부url요청 통로 열기.
        RestTemplate template = new RestTemplate();

        String url = "https://kapi.kakao.com/v1/payment/ready";
        // template으로 값을 보내고 받아온 ReadyResponse값 readyResponse에 저장.
        KakaoPayReady kakaoPayReady = template.postForObject(url, requestEntity, KakaoPayReady.class);
        kakaoPayReady.setPartner_order_id(member.getEmail());
        System.out.println(kakaoPayReady);
        // 받아온 값 return
        return kakaoPayReady;
    }

    // 결제 승인요청 메서드
    public KakaoPayApprove payApprove(String tid, String pgToken, Member member) {

        long bookingNum = 1; //테스트용 코드
        Optional<UserBooking> userBooking = bookingRepository.findById(bookingNum);

        // request값 담기.
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("cid", "TC0ONETIME");
        parameters.add("tid", tid); //결제번호
        parameters.add("partner_order_id", String.valueOf(bookingNum)); //주문번호.그냥 예약번호 사용하자
        parameters.add("partner_user_id", member.getEmail());
        parameters.add("pg_token", pgToken);

        // 하나의 map안에 header와 parameter값을 담아줌.
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부url 통신
        RestTemplate template = new RestTemplate();
        String url = "https://kapi.kakao.com/v1/payment/approve";
        // 보낼 외부 url, 요청 메시지(header,parameter), 처리후 값을 받아올 클래스.
        KakaoPayApprove kakaoPayApprove = template.postForObject(url, requestEntity, KakaoPayApprove.class);
        System.out.println("결재승인 응답객체: " + kakaoPayApprove);

        return kakaoPayApprove;
    }
    // header() 셋팅
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK 72ea3b3de7de27c82632e5ef2ebae7cd");
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return headers;
    }

}
