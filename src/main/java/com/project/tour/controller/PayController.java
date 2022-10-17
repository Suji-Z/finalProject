//package com.project.tour.controller;
//
//import com.project.tour.domain.*;
//import com.project.tour.service.KakaoPayService;
//import com.project.tour.service.MemberService;
//import com.project.tour.service.PayService;
//import com.project.tour.service.UserBookingService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/pay")
//public class PayController {
//
//    private final UserBookingService userBookingService;
//    private final PayService payService;
//    private final MemberService memberService;
//
//    //마이페이지에서 결제대기 누르면 넘어오는 결제 페이지
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping
//    public String getPay(Model model, Principal principal, PayForm payForm, UserBookingForm userBookingForm){
//
//        long bookingNum = 1; //테스트용 코드 마이페이지에서 결제대기상태를 누르면 가지고 오게
//
//        //userBooking 정보 넘기기
//        UserBooking userBooking = userBookingService.getUserBooking(bookingNum);
//        model.addAttribute("userBooking",userBooking);
//
//        return "booking-pay/payment";
//    }
//
//    //결제 데이터 저장
//    @PreAuthorize("isAuthenticated()")
//    @PostMapping("/confirmation/{id}")
//    public String paying(@Validated PayForm payForm, @Validated UserBookingForm userBookingForm, BindingResult bindingResult,
//                         Principal principal, Model model, @PathVariable("id") Long id){
//
//        if(bindingResult.hasErrors()){
//            return "booking-pay/booking";
//        }
//
//        //데이터 저장할 때 넘길 정보 : userbooking,member
//        UserBooking userBooking = userBookingService.getUserBooking(id);
//        Member member = memberService.getMember(principal.getName());
//
//        //1. pay 테이블 데이터 저장
//        payService.create(userBooking, member, payForm);
//
//        //2. userBooking 테이블 bookingStatus 데이터 수정
//        userBookingForm.setBookingStatus(2); //form 예약 상태 변경
//        userBookingService.modifyBookingStatus(userBooking, userBookingForm);
//
//        return "main";
//    }
//
//    //카카오 간편 결제 기능 - 1. 결제 요청
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/kakaopay")
//    @ResponseBody
//    public KakaoPayReady kakaoPayReady(@RequestParam(name = "total_amount") int totalAmount, Principal principal,
//                                       Model model) {
//        System.out.println("여기오나요");
//        Member member = memberService.getMember(principal.getName());
//
//        // 카카오 결제 준비하기	- 결제요청 service 실행.
//        KakaoPayReady kakaoPayReady = KakaoPayService.payReady(totalAmount,member);
//        // 요청처리후 받아온 결재고유 번호(tid)를 모델에 저장
//        model.addAttribute("tid", kakaoPayReady.getTid());
//        //userBooking 정보 넘기기
//        long bookingNum = 1; //테스트용 코드 마이페이지에서 결제대기상태를 누르면 가지고 오게
//
//        UserBooking userBooking = userBookingService.getUserBooking(bookingNum);
//        model.addAttribute("userBooking",userBooking);
//
//        return kakaoPayReady; // 클라이언트에 보냄.(tid,next_redirect_pc_url이 담겨있음.)
//    }
//
//    // 결제승인요청
//    @GetMapping("/order/pay/completed")
//    public String payCompleted(@RequestParam("pg_token") String pgToken, @ModelAttribute("tid") String tid, @ModelAttribute("order") Order order,  Model model) {
//
//        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
//        log.info("주문정보: " + order);
//        log.info("결재고유 번호: " + tid);
//
//        // 카카오 결재 요청하기
//        ApproveResponse approveResponse = kakaopayService.payApprove(tid, pgToken);
//
//        // 5. payment 저장
//        //	orderNo, payMathod, 주문명.
//        // - 카카오 페이로 넘겨받은 결재정보값을 저장.
//        Payment payment = Payment.builder()
//                .paymentClassName(approveResponse.getItem_name())
//                .payMathod(approveResponse.getPayment_method_type())
//                .payCode(tid)
//                .build();
//
//        orderService.saveOrder(order,payment);
//
//        return "redirect:/orders";
//    }
//    // 결제 취소시 실행 url
//    @GetMapping("/order/pay/cancel")
//    public String payCancel() {
//        return "redirect:/carts";
//    }
//
//    // 결제 실패시 실행 url
//    @GetMapping("/order/pay/fail")
//    public String payFail() {
//        return "redirect:/carts";
//    }
//
//
//}
