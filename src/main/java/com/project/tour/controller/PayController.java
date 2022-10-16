package com.project.tour.controller;

import com.project.tour.domain.UserBooking;
import com.project.tour.service.UserBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PayController {

    private final UserBookingService userBookingService;

    @GetMapping
    public String getPay(Model model, Principal principal){

        long bookingNum = 14; //테스트용 코드 마이페이지에서 결제대기상태를 누르면 가지고 오게

        //userBooking 정보 넘기기
        UserBooking userBooking = userBookingService.getUserBooking(bookingNum);
        model.addAttribute("userBooking",userBooking);



        return "booking-pay/payment";
    }

}
