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

    private final ShortReviewReplyService shortReviewReplyService;





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

        model.addAttribute("shortReviewList",shortReviewService.getshortReviewList(packageNum));
        model.addAttribute("package", packages);
        model.addAttribute("name",member.getName());
        model.addAttribute("email", member.getEmail());


        //데이터저장
        shortReviewService.create(content,score,member,packages);


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
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }


       shortReviewService.update(shortReviewNum,packageNum,content);
        model.addAttribute("shortReviewList",shortReviewService.getshortReviewList(packageNum));
        model.addAttribute("member",member);



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
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        shortReviewService.delete(shortReviewNum);
        model.addAttribute("shortReviewList", shortReviewService.getshortReviewList(packageNum));
        model.addAttribute("member",member);

        return "abroadPackage/packagedetail :: #shortReview";


    }



//
//
//
//
//    // 텍스트 리뷰 댓글 작성
//    @RequestMapping("/shortreply/{id}")
//    public String createShortReply(Model model, @PathVariable("id") Long id, Principal principal,
//                                   @PageableDefault Pageable pageable, ShortReviewReplyForm shortReviewReplyForm,
//                                   BindingResult bindingResult){
//
//        ShortReview shortReview = shortReviewService.getshortReview(id);
//        Member userName = memberService.getMember(principal.getName());
//
//        if(bindingResult.hasErrors()){
//            model.addAttribute("shortReview",shortReview);
//            return "abroadPackage/packagedetail"; //실패하면 디테일 띄우기
//
//        }
//
//        System.out.println("쇼트리뷰:" + shortReview.getId());
//        System.out.println("패키지:" + shortReview.getPackages().getId());
//
//
//        ShortReviewReply shortReviewReply = shortReviewReplyService.create(shortReview, shortReviewReplyForm.getContent(),userName);
//
//        return String.format("redirect:/package/%s#shortReply%s",
//                shortReviewReply.getShortReviewNum().getId(),shortReviewReply.getId());
//
//    }






}
