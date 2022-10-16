package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
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
    @Autowired private final CouponService couponService;

    //총 예약 금액
    int bookingTotalPrice = 0;


    /* 예약하기 디테일 띄우기 */
    @PreAuthorize("isAuthenticated()") //로그인 안하면 접근불가
    //@GetMapping("/detail/{packageNum}/{departureDate}")
    @GetMapping("/detail")
    public String bookingDetail(Model model, Principal principal, UserBookingForm userBookingForm
                                //, @PathVariable("packageNum") long packageNum, @PathVariable("departureDate") LocalDateTime departureDate
                                ) {

        //예약하기 버튼 누르면 package table의 packageNum이랑 depatureDate를 들고 옴.
        //패키지 디테일에서 예약하기로 넘어오면 띄울 창에 list 쓰기
        //user table, package table 끌고 오기

        long packageNum=1;
        int packageId=1;
        String departureDate="20220101";

        //1. packageNum에 맞는 packageData 넘기기
        Package apackage = packageService.getPackage(packageNum);
        model.addAttribute("apackage",apackage);

        //2. packageNum과 depatureDate에 맞는 여행경비 넘기기
        PackageDate packageDate = packageDateService.getPackageDate(packageId, departureDate);
        model.addAttribute("packageDate",packageDate);

        //3. user에 맞는 memberData 넘기기
        String email = principal.getName();  //login 아이디(email) 정보 가져오기
        Member member = memberService.getMember(email);  //login 아이디를 매개변수로 넘겨서 memberData 끌고오기
        model.addAttribute("member", member);

        //4. user가 가지고 있는 coupon 번호와 동일한 coupon의 정보 넘기기
        String couponNum = member.getCoupons(); //1,2,3
        List<Coupon> coupons = couponService.getCoupon(couponNum);
        model.addAttribute("coupons",coupons);

        return "booking-pay/booking";
    }

    /*쿠폰 적용했을 때 */
    @GetMapping("/detail/applyCoupon")
    public @ResponseBody HashMap<String,Object> applyCoupon(@RequestParam("chkCoupon") String chkCoupon,
                                                            @RequestParam("bookingPrice") int bookingPrice) throws Exception{

        //쿠폰선택시 비동기 ajax
        Coupon coupon = couponService.getApplyCoupon(chkCoupon);

        //할인금액,총 금액 계산
        int couponDiscountPrice = (int)(bookingPrice*coupon.getCouponRate()*(-1)); //할인금액
        bookingTotalPrice = (int)(bookingPrice*(1-coupon.getCouponRate())); //총금액

        //json형태 데이터로 넘기기
        HashMap<String,Object> couponInfo = new HashMap<String,Object>();
        couponInfo.put("couponName",coupon.getCouponName());
        couponInfo.put("couponDiscountPrice",couponDiscountPrice);
        couponInfo.put("bookingTotalPrice",bookingTotalPrice);

        return couponInfo;
    }

    /* 예약확인 저장 */
    @PreAuthorize("isAuthenticated()") //로그인 안하면 접근불가
    @PostMapping("/confirmation")
    public String confirmation(@Validated UserBookingForm userBookingForm, BindingResult bindingResult,
                               Principal principal) {

        System.out.println("여기");
        System.out.println(userBookingForm.getDeparture()+"이거");
        System.out.println(userBookingForm.getRequest());
        System.out.println(userBookingForm.getTravelPeriod());


        //bookingTotalPrice 검증
        if(bookingTotalPrice == 0){
            bookingTotalPrice = userBookingForm.getBookingTotalPrice();
        }


        if(bindingResult.hasErrors()){
            return "booking-pay/booking"; // 이걸 더 간단하게는 못할까?
        }


        return "booking-pay/booking_confirmation";

    }

}
