package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.service.MemberService;
import com.project.tour.service.PackageDateService;
import com.project.tour.service.PackageService;
import com.project.tour.service.UserBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    //테이블 2개 : member,package
    // 의존성 자동주입 오류라 수동으로도 넣어줌
    @Autowired private final MemberService memberService;
    @Autowired private final PackageService packageService;
    @Autowired private final PackageDateService packageDateService;
    @Autowired private final UserBookingService userBookingService;

    /* 예약하기 디테일 띄우기 */
    @PreAuthorize("isAuthenticated()") //로그인 안하면 접근불가
    //@GetMapping("/detail/{packageNum}/{departureDate}")
    @GetMapping("/detail")
    public String bookingDetail(Model model, Principal principal
                                //, @PathVariable("packageNum") long packageNum, @PathVariable("departureDate") LocalDateTime departureDate
                                ) {

        //예약하기 버튼 누르면 package table의 packageNum이랑 depatureDate를 들고 옴.
        //패키지 디테일에서 예약하기로 넘어오면 띄울 창에 list 쓰기
        //user table, package table 끌고 오기

        long packageNum=2;
        String departureDate="20220101";

        //1. packageNum에 맞는 packageData 넘기기
        Package apackage = packageService.getPackage(packageNum);
        model.addAttribute("apackage",apackage);

        //2. packageNum과 depatureDate에 맞는 여행경비 넘기기
        PackageDate packageDate = packageDateService.getPackageDate(packageNum, departureDate);
        model.addAttribute("packageDate",packageDate);

        //3. user에 맞는 memberData 넘기기
        String email = principal.getName();  //login 아이디(email) 정보 가져오기
        Member member = memberService.getMember(email);  //login 아이디를 매개변수로 넘겨서 memberData 끌고오기
        model.addAttribute("member", member);

        return "booking-pay/booking";
    }

    /* 예약확인 저장 */
//    @PreAuthorize("isAuthenticated()") //로그인 안하면 접근불가
//    @GetMapping("/confirmation")
//    public String confirmation(@Validated UserBookingForm userBookingForm, BindingResult bindingResult,
//                               Model model, Principal principal, @PathVariable("packageNum") long packageNum,
//                               @PathVariable("departureDate") LocalDateTime departureDate) {
//
//        //1. packageNum에 맞는 packageData 넘기기
//        Package apackage = packageService.getPackage(packageNum);
//        model.addAttribute("apackage",apackage);
//
//        //2. packageNum과 depatureDate에 맞는 여행경비 넘기기
//        PackageDate packageDate = packageDateService.getPackageDate(packageNum,departureDate);
//        model.addAttribute("packageDate",packageDate);
//
//        //3. user에 맞는 memberData 넘기기
//        String email = principal.getName();  //login 아이디(email) 정보 가져오기
//        Member member = memberService.getMember(email);  //login 아이디를 매개변수로 넘겨서 memberData 끌고오기
//        model.addAttribute("member", member);
//
//        if(bindingResult.hasErrors()){
//            return "booking-pay/booking"; // 이걸 더 간단하게는 못할까?
//        }
//
//        userBookingService.create(userBookingForm); //데이터 저장
//
//        return "booking-pay/booking_confirmation";
//
//    }

}
