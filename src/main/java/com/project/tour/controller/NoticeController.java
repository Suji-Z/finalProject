package com.project.tour.controller;

import com.project.tour.domain.UserBookingForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping()
    public String bookingDetail() {
        return "notice/notice_list";
    }

}
