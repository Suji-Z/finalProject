package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.service.MemberService;
import com.project.tour.service.MypageService;
import com.project.tour.service.ReviewReplyService;
import com.project.tour.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MemberService memberService;

    private final MypageService mypageService;




    @PreAuthorize("isAuthenticated()")
    @GetMapping (value = "/")
    public String main(Model model, Principal principal){

        Member member = memberService.getMember(principal.getName());

        model.addAttribute("member",member);

        return "mypage/mypage_main";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/cancelList")
    public String cancel_list(){

        return "mypage/mypage_bookingCancel_List";

    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/bookingList")
    public String booking_list(){

        return "mypage/mypage_bookingList";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/coupon")
    public String coupon(){

        return "mypage/mypage_coupon";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/point")
    public String point(){

        return "mypage/mypage_point";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/update")
    public String update1(Model model, Principal principal, MemberCreate memberCreate){

        Member member = memberService.getMember(principal.getName());

        memberCreate.setBirth(member.getBirth());
        memberCreate.setEmail(member.getEmail());
        memberCreate.setName(member.getName());
        memberCreate.setEmail(member.getEmail());
        memberCreate.setPhone_num(member.getPhone());
        memberCreate.setPassword1(member.getPassword());

        String keywords = member.getKeyword();

        System.out.println(keywords);

        String words[] = keywords.split(",");

        String keyword = "";

        for(int i = 0;i<words.length;i++){
           keyword = words[i];

        }


        return "mypage/mypage_profileUpdate";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/update")
    public String update2(){




        return "mypage/mypage_profileUpdate";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/qna")
    public String qna(){

        return "mypage/mypage_q&a_list";

    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/reviewList")
    public String review_list(Model model, Principal principal){

        Member member = memberService.getMember(principal.getName());

        Long memberId = member.getId();

        List<Review> mypageReview = mypageService.getMypageReview(memberId);

        model.addAttribute("mypageReview",mypageReview);

        //System.out.println(mypageReview.size());

        return "mypage/mypage_reviewList";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/estimateList")
    public String estimate_list(Model model, Principal principal){

        Member member = memberService.getMember(principal.getName());

        String email = member.getEmail();

        List<EstimateInquiry> mypageEstimate = mypageService.getMypageEstimate(email);

        model.addAttribute("mypageEstimate",mypageEstimate);

        System.out.println(mypageEstimate.size());


        return "mypage/mypage_estimateList";

    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/unregister")
    public String unregister(){

        return "mypage/mypage_unregister";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/voicecusList")
    public String voicecus_list(){

        return "mypage/mypage_voicecus_list";

    }
}
