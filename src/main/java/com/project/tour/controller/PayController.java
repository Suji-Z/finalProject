package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.kakao.service.KakaoPayService;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.PayService;
import com.project.tour.service.UserBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public String getPay(Model model, @LoginUser SessionUser user, Principal principal, PayForm payForm, UserBookingForm userBookingForm){

        long bookingNum = 21; //테스트용 코드 마이페이지에서 결제대기상태를 누르면 가지고 오게

        //로그인 정보
        Member member;
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        model.addAttribute("member",member);

        //userBooking 정보 넘기기
        UserBooking userBooking = userBookingService.getUserBooking(bookingNum);
        model.addAttribute("userBooking",userBooking);
        ;
        return "booking-pay/payment";
    }

    //결제 데이터 저장
    @GetMapping("/payments/complete")
    public String confirmPay(@RequestParam("impUid") String impUid, @RequestParam("merchantUid") String merchantUid,
                                               @RequestParam("payMethod") String payMethod, @RequestParam("payTotalPrice") int payTotalPrice,
                                               PayForm payForm, Principal principal, @LoginUser SessionUser user,
                                             @RequestParam("bookingNum") long id, UserBookingForm userBookingForm, Model model){

        System.out.println("여기오나요");

        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //로그인 정보
        Member member;
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        //payForm에 데이터 저장
        payForm.setPayMethod(payMethod);
        payForm.setPayInfo(impUid);
        payForm.setTotalPrice(payTotalPrice);
        payDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS"));

        //데이터 저장할 때 넘길 정보 : userbooking,member
        UserBooking userBooking = userBookingService.getUserBooking(id);

        //1. pay 테이블 데이터 저장
        payService.create(userBooking, member, payDate, payForm);

        //2. userBooking 테이블 bookingStatus 데이터 수정
        userBookingForm.setBookingStatus(2); //form 예약 상태 변경
        userBookingService.modifyBookingStatus(userBooking, userBookingForm);

        System.out.println("여기오나요2");

        return "redirect:/pay/complete";

    }

    //저장 후 결제완료 창 띄우기 > 다 되는데 return이 안됨..
    @GetMapping("/complete")
    public String confirmation(Model model, @LoginUser SessionUser user, Principal principal){

        System.out.println("여기는 오나요 3");
        //로그인 정보
        Member member;
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        //confirmation에 띄울 정보
        //member정보
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

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/confirmation/{id}") //id : bookingNum
//    @ResponseBody
//    public ResponseEntity<?> paying(PayForm payForm, UserBookingForm userBookingForm, Principal principal,
//                                    @LoginUser SessionUser user, Model model, @PathVariable("id") Long id,
//                                    @RequestParam("impUid") String impUid,
//                                    @RequestParam("payMethod") String payMethod, @RequestParam("payTotalPrice") int payTotalPrice){
//
//        //로그인 정보
//        Member member;
//        if(memberService.existByEmail(principal.getName())){
//            member = memberService.getName(principal.getName());
//        }else{
//            member = memberService.getName(user.getEmail());
//        }
//
//        //payForm에 데이터 저장
//        payForm.setPayMethod(payMethod);
//        payForm.setPayInfo(impUid);
//        payForm.setTotalPrice(payTotalPrice);
//        payDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS"));
//
//        //데이터 저장할 때 넘길 정보 : userbooking,member
//        UserBooking userBooking = userBookingService.getUserBooking(id);
//
//        //1. pay 테이블 데이터 저장
//        payService.create(userBooking, member, payDate, payForm);
//
//        //2. userBooking 테이블 bookingStatus 데이터 수정
//        userBookingForm.setBookingStatus(2); //form 예약 상태 변경
//        userBookingService.modifyBookingStatus(userBooking, userBookingForm);
//
//        return new ResponseEntity("/pay/hello", HttpStatus.OK);
//    }




}