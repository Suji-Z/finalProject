package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.kakao.vo.KakaoPayApprove;
import com.project.tour.kakao.vo.KakaoPayReady;
import com.project.tour.kakao.service.KakaoPayService;
import com.project.tour.service.MemberService;
import com.project.tour.service.PayService;
import com.project.tour.service.UserBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PayController {

    private final UserBookingService userBookingService;
    private final PayService payService;
    private final MemberService memberService;
    private final KakaoPayService kakaoPayService;

    String payDate ="";

    //마이페이지에서 결제대기 누르면 넘어오는 결제 페이지
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String getPay(Model model, Principal principal, PayForm payForm, UserBookingForm userBookingForm){

        long bookingNum = 71; //테스트용 코드 마이페이지에서 결제대기상태를 누르면 가지고 오게

        //userBooking 정보 넘기기
        UserBooking userBooking = userBookingService.getUserBooking(bookingNum);
        model.addAttribute("userBooking",userBooking);
;
        return "booking-pay/payment";
    }

    //결제 데이터 저장
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/confirmation/{id}") //id : bookingNum
    @ResponseBody
    public ResponseEntity<?> paying(PayForm payForm, UserBookingForm userBookingForm,
                                 Principal principal, Model model, @PathVariable("id") Long id,
                                 @RequestParam("impUid") String impUid,
                                 @RequestParam("payMethod") String payMethod, @RequestParam("payTotalPrice") int payTotalPrice){

        //payForm에 데이터 저장
        payForm.setPayMethod(payMethod);
        payForm.setPayInfo(impUid);
        payForm.setTotalPrice(payTotalPrice);
        payDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS"));

        //데이터 저장할 때 넘길 정보 : userbooking,member
        UserBooking userBooking = userBookingService.getUserBooking(id);
        Member member = memberService.getMember(principal.getName());

        //1. pay 테이블 데이터 저장
        payService.create(userBooking, member, payDate, payForm);

        //2. userBooking 테이블 bookingStatus 데이터 수정
        userBookingForm.setBookingStatus(2); //form 예약 상태 변경
        userBookingService.modifyBookingStatus(userBooking, userBookingForm);

        return new ResponseEntity("/pay/hello", HttpStatus.OK);
    }

    @GetMapping("/payments/complete")
    public @ResponseBody HashMap<String,Object> confirmPay(@RequestParam("impUid") String impUid, @RequestParam("merchantUid") String merchantUid,
                             @RequestParam("payMethod") String payMethod, @RequestParam("payTotalPrice") String payTotalPrice,
                             PayForm payForm){

        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //json형태 데이터로 넘기기
        HashMap<String,Object> payInfo = new HashMap<String,Object>();
        payInfo.put("payMethod",payMethod);
        payInfo.put("payTotalPrice",payTotalPrice);

        return payInfo;

    }

    @GetMapping("/hello")
    public String confirmation(Model model, Principal principal){

        //confirmation에 띄울 정보
        //member정보
        Member member = memberService.getMember(principal.getName());
        model.addAttribute("member",member);

        //pay 테이블 데이터 들고오기
        long payNum = payService.getPayNum(member, payDate);
        Pay pay = payService.getPay(payNum);
        model.addAttribute("pay",pay);

        System.out.println("pay.getPayTotalPrice() = " + pay.getPayTotalPrice());

        return "booking-pay/payment_confirmation";

    }

    @GetMapping("/payments/fail")
    public String confirmPay1(){

        return "booking-pay/payment_fail";

    }




}
