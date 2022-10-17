//package com.project.tour.service;
//
//import com.project.tour.domain.KakaoPayApprove;
//import com.project.tour.domain.KakaoPayReady;
//import com.project.tour.domain.Member;
//import com.project.tour.domain.UserBooking;
//import com.project.tour.repository.BookingRepository;
//import com.project.tour.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.java.Log;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//import java.security.Principal;
//
//@Log
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class KakaoPayService {
//
////    private UserBookingService userBookingService; //예약내역 가져오기
////    private MemberService memberService;
//
//        private final MemberRepository memberRepository;
//        private final BookingRepository bookingRepository;
//
//    public KakaoPayReady payReady(int totalAmount, Member member) {
//
//        int bookingNum = 1; //테스트용 코드
//        UserBooking userBooking = bookingRepository.
//
//        String itemName = userBooking.getAPackage().getPackageName();
//        String order_id = Integer.toString(bookingNum);
//
//        // 카카오가 요구한 결제요청request값을 담아줍니다.
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
//        parameters.add("cid", "TC0ONETIME");
//        parameters.add("partner_order_id", order_id); //주문번호.그냥 예약번호 사용하자
//        parameters.add("partner_user_id", member.getEmail());
//        parameters.add("item_name", itemName);
//        parameters.add("quantity", "1");
//        parameters.add("total_amount", String.valueOf(totalAmount));
//        parameters.add("tax_free_amount", "0");
//        parameters.add("approval_url", "http://localhost/order/pay/completed"); // 결제승인시 넘어갈 url
//        parameters.add("cancel_url", "http://localhost/order/pay/cancel"); // 결제취소시 넘어갈 url
//        parameters.add("fail_url", "http://localhost/order/pay/fail"); // 결제 실패시 넘어갈 url
//
//        System.out.println("파트너주문아이디:"+ parameters.get("partner_order_id"));
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
//        // 외부url요청 통로 열기.
//        RestTemplate template = new RestTemplate();
//        String url = "https://kapi.kakao.com/v1/payment/ready";
//        // template으로 값을 보내고 받아온 ReadyResponse값 readyResponse에 저장.
//        KakaoPayReady kakaoPayReady = template.postForObject(url, requestEntity, KakaoPayReady.class);
//        System.out.println("결재준비 응답객체: " + kakaoPayReady);
//        // 받아온 값 return
//        return kakaoPayReady;
//    }
//
//    // 결제 승인요청 메서드
//    public KakaoPayApprove payApprove(String tid, String pgToken, Principal principal) {
//
//        int bookingNum = 1; //테스트용 코드
//        Member member= memberService.getMember(principal.getName());
//        UserBooking userBooking = userBookingService.getUserBooking(bookingNum);
//
//        // 주문명 만들기.
//        String itemName = userBooking.getAPackage().getPackageName();
//        String order_id = Integer.toString(bookingNum);
//
//        // request값 담기.
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
//        parameters.add("cid", "TC0ONETIME");
//        parameters.add("tid", tid); //결제번호
//        parameters.add("partner_order_id", order_id); //주문번호.그냥 예약번호 사용하자
//        parameters.add("partner_user_id", principal.getName());
//        parameters.add("pg_token", pgToken);
//
//        // 하나의 map안에 header와 parameter값을 담아줌.
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
//
//        // 외부url 통신
//        RestTemplate template = new RestTemplate();
//        String url = "https://kapi.kakao.com/v1/payment/approve";
//        // 보낼 외부 url, 요청 메시지(header,parameter), 처리후 값을 받아올 클래스.
//        KakaoPayApprove kakaoPayApprove = template.postForObject(url, requestEntity, KakaoPayApprove.class);
//        System.out.println("결재승인 응답객체: " + kakaoPayApprove);
//
//        return kakaoPayApprove;
//    }
//    // header() 셋팅
//    private HttpHeaders getHeaders() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "72ea3b3de7de27c82632e5ef2ebae7cd");
//        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        return headers;
//    }
//
//}
