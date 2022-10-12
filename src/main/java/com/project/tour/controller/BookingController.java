package com.project.tour.controller;

import com.project.tour.domain.Member;
import com.project.tour.domain.Package;
import com.project.tour.domain.PackageDate;
import com.project.tour.service.MemberService;
import com.project.tour.service.PackageDateService;
import com.project.tour.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Autowired  //의존성 자동주입 오류라 수동으로도 넣어줌
    private MemberService memberService;
    private PackageService packageService;
    private PackageDateService packageDateService;

    @PreAuthorize("isAuthenticated()") //로그인 안하면 접근불가
    @GetMapping("/detail/{packageNum}/{departureDate}")
    public String bookingDetail(Model model, @PathVariable("packageNum") long packageNum,
                                @PathVariable("departureDate") LocalDateTime departureDate, Principal principal) {

        //예약하기 버튼 누르면 package table의 packageNum이랑 depatureDate를 들고 옴.
        //패키지 디테일에서 예약하기로 넘어오면 띄울 창에 list 쓰기
        //user table, package table 끌고 오기

        //1. packageNum에 맞는 packageData 넘기기
        Package packageData = packageService.getPackage(packageNum);
        model.addAttribute("packagesData",packageData);

        //2. packageNum과 depatureDate에 맞는 여행경비 넘기기
        PackageDate packageDate = packageDateService.getPackageDate(packageNum,departureDate);
        model.addAttribute("packagesData",packageData);


        //3. user에 맞는 memberData 넘기기
        String email = principal.getName();  //login 아이디(email) 정보 가져오기
        Member memberData = memberService.getMember(email);  //login 아이디를 매개변수로 넘겨서 memberData 끌고오기
        model.addAttribute("memberData", memberData);

        return "booking-pay/booking";
    }

    //@PreAuthorize("isAuthenticated()")
    @GetMapping("/confirmation")
    public String confirmation() {

        //userbooking table 끌고 오기

        return "booking-pay/booking_confirmation";

    }

}
