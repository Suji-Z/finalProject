package com.project.tour.controller;

import com.project.tour.domain.Member;
import com.project.tour.domain.NoticeForm;
import com.project.tour.domain.ReviewForm;
import com.project.tour.domain.UserBookingForm;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final MemberService memberService;
    private final NoticeService noticeService;

    //글작성 페이지 띄우기 : 관리자만 가능
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/write")
    public String noticeWrite(@LoginUser SessionUser user, Principal principal,
                              NoticeForm noticeForm) {

        return "notice/notice_create";
    }

    //글작성 데이터 저장하기
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/write")
    public String noticeWrite2(@Valid NoticeForm noticeForm, BindingResult bindingResult){

        System.out.println("얍");

        noticeService.create(noticeForm);

        return"notice/notice_create";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String bookingList() {
        return "notice/notice_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article")
    public String bookingArticle(@LoginUser SessionUser user, Principal principal) {



        return "notice/notice_article";
    }



}
