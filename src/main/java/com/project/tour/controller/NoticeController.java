package com.project.tour.controller;

import com.project.tour.domain.UserBookingForm;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
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

    @GetMapping("/list")
    public String bookingList() {
        return "notice/notice_list";
    }

    @GetMapping("/article")
    public String bookingArticle(@LoginUser SessionUser user, Principal principal) {



        return "notice/notice_article";
    }

    @GetMapping("/create")
    public String bookingCreate() {
        return "notice/notice_create";
    }

}
