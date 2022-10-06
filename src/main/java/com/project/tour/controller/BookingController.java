package com.project.tour.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    @GetMapping("/list")
    public String bookingDetail() {
        return "booking-pay/booking";
    }

    @GetMapping("/confirmation")
    public String confirmation() {
        return "booking-pay/booking_confirmation";

    }

}
