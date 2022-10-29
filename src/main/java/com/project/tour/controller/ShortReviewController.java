package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/package")
public class ShortReviewController {

    private final PackageService packageService;

    private final MemberService memberService;

    private final ShortReviewService shortReviewService;

    private final PackageDateService packagedateService;






    //텍스트 리뷰 작성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/shortReview/create")
    public String createShortReview(Model model, Principal principal,@LoginUser SessionUser user,
                                    @RequestParam("packageNum") Long packageNum,
                                    @RequestParam("content") String content,
                                    @RequestParam("score") Double score
                                    )  throws IOException {


        Package packages = packageService.getPackage(packageNum);

        Member member;


        if (memberService.existByEmail(principal.getName())) {
            member = memberService.getName(principal.getName());

            //결제완료된 부킹리스트 가져오기
            Long memberId = member.getId();
            int status = 2; // 0:예약확인중 1:결제대기중 2:결제완료

            List<UserBooking> bookingShortReview =  shortReviewService.getBookingShortReview(memberId,status,packages);

            model.addAttribute("bookingShortReview",bookingShortReview);

        } else {
            member = memberService.getName(user.getEmail());
//            결제완료된 부킹리스트 가져오기
            Long memberId = member.getId();
            int status = 2; // 0:예약확인중 1:결제대기중 2:결제완료

            List<UserBooking> bookingShortReview =  shortReviewService.getBookingShortReview(memberId,status,packages);

            model.addAttribute("bookingShortReview",bookingShortReview);

        }




        //데이터저장
        shortReviewService.create(content,score,member,packages);

        List<ShortReview> shortReviewList = shortReviewService.getshortReviewList(packageNum);
        Integer size = shortReviewList.size();

        model.addAttribute("shortReviewList",shortReviewService.getshortReviewList(packageNum));
        model.addAttribute("name",member.getName());
        model.addAttribute("email", member.getEmail());
        model.addAttribute("size", size);





        return "abroadPackage/packagedetail :: #shortReview";
    }





    //텍스트 리뷰 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/shortReview/update")
    public String updateShortReview(@RequestParam("packageNum") Long packageNum,
                         @RequestParam("shortReviewNum") Long shortReviewNum,
                         @RequestParam("content") String content,
                         Principal principal, @LoginUser SessionUser user,
                         Model model){

        //로그인 확인
        Member member;
        String  email;


        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
            email = member.getEmail();

        }else{
            member = memberService.getName(user.getEmail());
            email = member.getEmail();
        }


       shortReviewService.update(shortReviewNum,packageNum,content);

        List<ShortReview> shortReviewList = shortReviewService.getshortReviewList(packageNum);
        Integer size = shortReviewList.size();


        model.addAttribute("shortReviewList",shortReviewService.getshortReviewList(packageNum));
        model.addAttribute("email",email);
        model.addAttribute("size",size);



        return "abroadPackage/packagedetail :: #shortReview";

    }


    //텍스트 리뷰 삭제
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/shortReview/delete")
    public String noticeReplyDelete(@RequestParam("shortReviewNum") Long shortReviewNum, @RequestParam("packageNum") Long packageNum,
                                    Principal principal, @LoginUser SessionUser user,
                                    Model model){

        //로그인 확인
        Member member;
        String email;


        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
            email = member.getEmail();
        }else{
            member = memberService.getName(user.getEmail());
            email = member.getEmail();
        }

        shortReviewService.delete(shortReviewNum);

        List<ShortReview> shortReviewList = shortReviewService.getshortReviewList(packageNum);
        Integer size = shortReviewList.size();

        model.addAttribute("shortReviewList", shortReviewService.getshortReviewList(packageNum));
        model.addAttribute("member",member);
        model.addAttribute("size",size);
        model.addAttribute("email", email);


        return "abroadPackage/packagedetail :: #shortReview";


    }






}
