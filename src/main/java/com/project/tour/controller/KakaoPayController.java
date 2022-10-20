package com.project.tour.controller;

import com.project.tour.domain.Member;
import com.project.tour.domain.UserBooking;
import com.project.tour.kakao.service.KakaoPayService;
import com.project.tour.kakao.vo.KakaoPayApprove;
import com.project.tour.kakao.vo.KakaoPayReady;
import com.project.tour.service.MemberService;
import com.project.tour.service.PayService;
import com.project.tour.service.UserBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class KakaoPayController {

    //카카오 페이 간편결제 기능 구현 하던 내용

    private final UserBookingService userBookingService;
    private final PayService payService;
    private final MemberService memberService;
    private final KakaoPayService kakaoPayService;


    //카카오 간편 결제 기능 - 1. 결제 요청
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/kakaopay")
    @ResponseBody
    public KakaoPayReady kakaoPayReady(@RequestParam(name = "total_amount") int totalAmount, Principal principal,
                                       Model model) {
        //payMethod가 카카오페이면 카카오 결제를 하고 아니면 바로 결제테이블 저장과정 거치기

        // 카카오 결제 준비하기	- 결제요청 service 실행.
        Member member = memberService.getMember(principal.getName());
        KakaoPayReady kakaoPayReady = kakaoPayService.payReady(totalAmount, member);

        // 요청처리후 받아온 결재고유 번호(tid)를 모델에 저장
        model.addAttribute("tid", kakaoPayReady.getTid());
        //userBooking 정보 넘기기
        long bookingNum = 1; //테스트용 코드 마이페이지에서 결제대기상태를 누르면 가지고 오게

        UserBooking userBooking = userBookingService.getUserBooking(bookingNum);
        model.addAttribute("userBooking",userBooking);

        return kakaoPayReady; // 클라이언트에 보냄.(tid,next_redirect_pc_url이 담겨있음.)
    }

    // 결제승인요청
    @RequestMapping("kakaopay/completed")
    public String payCompleted(@RequestParam("pg_token") String pgToken, @ModelAttribute("tid") String tid,
                               @ModelAttribute("userBooking") UserBooking userBooking, Model model,Principal principal) {

        //데이터 넘어오나 확인
        System.out.println("pgToken = " + pgToken);
        System.out.println("userBooking = " + userBooking.getTravelerName());
        System.out.println("tid = " + tid);

        // 카카오 결재 요청하기
        Member member = memberService.getMember(principal.getName());
        KakaoPayApprove kakaoPayApprove = kakaoPayService.payApprove(tid,pgToken,member);

        // 5. payment 저장
        //	orderNo, payMathod, 주문명.
        // - 카카오 페이로 넘겨받은 결재정보값을 저장.
//        Payment payment = Payment.builder()
//                .paymentClassName(approveResponse.getItem_name())
//                .payMathod(approveResponse.getPayment_method_type())
//                .payCode(tid)
//                .build();
//
//        orderService.saveOrder(order,payment);

        System.out.println("결제완료");

        return "booking-pay/payment_confirmation";
    }
    // 결제 취소시 실행 url
    @GetMapping("kakaopay/cancel")
    public String payCancel() {
        return "booking-pay/payment_fail";
    }

    // 결제 실패시 실행 url
    @GetMapping("kakaopay/fail")
    public String payFail() {
        return "booking-pay/payment_fail";
    }

    @PostMapping("payments/complete")
    public void confirmPay(@RequestParam("imp_uid") String imp_uid){

        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println("ㅇㄱ");

    }

}
