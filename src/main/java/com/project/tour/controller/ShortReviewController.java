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



//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("shortReview/create")
//    public String createShortReview1(Model model, Principal principal,@LoginUser SessionUser user) {
//
//        Member member;
//
//
//        if (memberService.existByEmail(principal.getName())) {
//            member = memberService.getName(principal.getName());
//
//
//
//        } else {
//            member = memberService.getName(user.getEmail());
//            //결제완료된 부킹리스트 가져오기
//            Long memberId = member.getId();
//            int status = 2; // 0:예약확인중 1:결제대기중 2:결제완료
//
//            List<UserBooking> bookingShortReview = shortReviewService.getBookingShortReview(memberId, status);
//
//            model.addAttribute("bookingShortReview", bookingShortReview);
//
//        }
//
//
//        return "abroadPackage/packagedetail :: #shortReview";
//    }



    //텍스트 리뷰 작성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("shortReview/create")
    public String createShortReview2(Model model, Principal principal,@LoginUser SessionUser user,
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


        model.addAttribute("package", packages);
        model.addAttribute("name",member.getName());
        model.addAttribute("email", member.getEmail());


        //데이터저장
        shortReviewService.create(content,score,member,packages);


        return "abroadPackage/packagedetail :: #shortReview";
    }

//    @RequestMapping (value = {"/article/{id}","/article/shortReply"})
//    public String article(Model model, @PathVariable("id") Long id, ShortReviewReplyForm shortReviewReplyForm,
//                          Principal principal,@LoginUser SessionUser user){
//
//        ShortReview shortReview = shortReviewService.getshortReview(id);
//
//        Member member;
//
//        if(memberService.existByEmail(principal.getName())){
//
//            member = memberService.getName(principal.getName());
//
//        }else{
//
//            member = memberService.getName(user.getEmail());
//
//        }
//
//        Long id2 = member.getId();
//
//
//        model.addAttribute("shortReview",shortReview);
//
//        return "abroadPackage/packagedetail";
//
//    }


//    @PreAuthorize("isAuthenticated()")
//    @GetMapping(value = "/update/{id}")
//    public String update1(Model model,ShortReviewForm shortReviewForm, @PathVariable("id") Long id, Principal principal){
//
//        ShortReview shortReview = shortReviewService.getshortReview(id);
//
//        if(!shortReview.getUserName().getEmail().equals(principal.getName())){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
//        }
//
//        shortReviewForm.setContent(shortReview.getContent());
//        shortReviewForm.setScore(shortReview.getScore());
//
//        Member member = memberService.getMember(principal.getName());
//
//        //결제완료된 부킹리스트 가져오기
////        Long memberId = member.getId();
////        int status = 2; // 0:예약확인중 1:결제대기중 2:결제완료
////
////        List<UserBooking> bookingReview =  reviewService.getBookingReview(memberId,status);
////
////        System.out.println(bookingReview.size());
////
////        model.addAttribute("bookingReview",bookingReview);
//
//        model.addAttribute("review", shortReview);
//
//        return "abroadPackage/packagedetail";
//
//    }


//    @PreAuthorize("isAuthenticated()")
//    @PostMapping(value = "/update/{id}")
//    public String update2(@Valid ShortReviewForm shortReviewForm, BindingResult bindingResult,
//                          @PathVariable("id") Long id, Principal principal,
//                          @RequestParam("packageNum") Long packageNum)  throws IOException {
//
//        if(bindingResult.hasErrors()){
//            return "abroadPackage/packagedetail";
//        }
//
//        ShortReview shortReview = shortReviewService.getshortReview(id);
//
//        if(!shortReview.getUserName().getEmail().equals(principal.getName())){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다");
//        }
//
//
//        Package packages = packageService.getPackage(packageNum);
//
//        shortReviewService.update(shortReview,shortReviewForm.getContent(),
//                shortReviewForm.getScore(),packages);
//
//        return String.format("redirect:/review/article/%s",id);
//
//    }
//
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
