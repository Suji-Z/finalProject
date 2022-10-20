package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.PackageDateService;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PayController {

    private final UserBookingService userBookingService;
    private final PayService payService;
    private final MemberService memberService;
    private final PackageDateService packageDateService;

    //마이페이지에서 결제대기 누르면 넘어오는 결제 페이지
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String getPay(Model model, @LoginUser SessionUser user, Principal principal, PayForm payForm, UserBookingForm userBookingForm){

        long bookingNum = 4; //테스트용 코드 마이페이지에서 결제대기상태를 누르면 가지고 오게

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
    public ResponseEntity<?> confirmPay(@RequestParam("impUid") String impUid, @RequestParam("merchantUid") String merchantUid,
                             @RequestParam("payMethod") String payMethod, @RequestParam("payTotalPrice") int payTotalPrice,
                             PayForm payForm, Principal principal, @LoginUser SessionUser user,
                             @RequestParam("bookingNum") long id, UserBookingForm userBookingForm, Model model){

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

        //데이터 저장할 때 넘길 정보 : userbooking,member
        UserBooking userBooking = userBookingService.getUserBooking(id);

        //1. pay 테이블 데이터 저장
        payService.create(userBooking, member, payForm);

        //2. userBooking 테이블 bookingStatus 데이터 수정
        userBookingService.modifyBookingStatus(userBooking, 2);

        //3. packageDate 테이블 remainCount 수정
        int bookingTotalCount = userBooking.getBookingTotalCount();
        PackageDate packageDate = packageDateService.getPackageDate(userBooking.getAPackage(), userBooking.getDeparture());
        packageDateService.modifyRemainCount(packageDate, bookingTotalCount);

        return new ResponseEntity("/pay/complete", HttpStatus.OK);

    }

    //저장 후 결제완료 창 띄우기 > 다 되는데 return이 안됨..
    @GetMapping("/complete")
    public String confirmation(Model model, @LoginUser SessionUser user, Principal principal){

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

        //제일 마지막에 결제된 pay정보를 들고 오기
        Pay pay = payService.getRecentPay();
        model.addAttribute("pay",pay);

        return "booking-pay/payment_confirmation";

    }

    @GetMapping("/payments/fail")
    public String confirmPay1(){

        return "booking-pay/payment_fail";

    }



}