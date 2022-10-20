package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    // 의존성 자동주입 오류라 수동으로도 넣어줌
    @Autowired private final MemberService memberService;
    @Autowired private final PackageService packageService;
    @Autowired private final PackageDateService packageDateService;
    @Autowired private final UserBookingService userBookingService;
    @Autowired private final CouponService couponService;

    /* 예약 디테일 띄우기 */
    @PreAuthorize("isAuthenticated()") //로그인 안하면 접근불가
    @PostMapping("/detail/{id}") //packageNum 가지고 오기
    public String bookingDetail(Model model, @LoginUser SessionUser user, Principal principal, UserBookingForm userBookingForm,
                                BookingDTO bookingform, @PathVariable("id") Long packageNum
                                ) throws Exception{

        //로그인 정보 확인
        Member member;
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        //예약하기 버튼 누르면 package table의 packageNum가져오기
        //user table, package table 끌고 오기
        String departureDate = bookingform.getDeparture().replaceAll("-", ""); //출발날짜 20220101

        //출발일로 도착일 계산하기
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();

        cal.setTime(format.parse(departureDate));  //형변환(String > Date > Calender)
        cal.add(Calendar.DATE, packageService.getPackage(packageNum).getTravelPeriod());
        String arrivalDate = format.format(cal.getTime());
        bookingform.setDeparture(departureDate);
        bookingform.setArrival(arrivalDate);

        //1. packageNum에 맞는 packageData 넘기기
        Package apackage = packageService.getPackage(packageNum);
        model.addAttribute("apackage",apackage);

        //2. packageNum과 depatureDate에 맞는 여행경비 넘기기
        PackageDate packageDate = packageDateService.getPackageDate(apackage, departureDate);
        model.addAttribute("packageDate",packageDate);

        //3. user에 맞는 memberData 넘기기
        model.addAttribute("member", member);

        //4. user가 가지고 있는 coupon 번호와 동일한 coupon의 정보 넘기기
        String couponNum = member.getCoupons(); //1,2,3
        List<Coupon> coupons = couponService.getCoupon(couponNum);
        model.addAttribute("coupons",coupons);

        // 출발날짜, 도착날짜, 인원수 다 넘기기
        // 도착날짜 연산해주고 보내기
        model.addAttribute("bookingForm",bookingform);

        return "booking-pay/booking";
    }

    /*쿠폰 적용했을 때 */
    @GetMapping("/detail/applyCoupon")
    public @ResponseBody HashMap<String,Object> applyCoupon(@RequestParam("chkCoupon") String chkCoupon, BookingPriceDTO priceForm,
                                                            @RequestParam("bookingPrice") int bookingPrice) throws Exception{

        //쿠폰선택시 비동기 ajax
        Coupon coupon = couponService.getApplyCoupon(chkCoupon);

        //할인금액,총 금액 계산
        priceForm.setCouponDiscountPrice((int)(bookingPrice*coupon.getCouponRate()*(-1))); //할인금액
        priceForm.setBookingTotalPrice((int)(bookingPrice*(1-coupon.getCouponRate()))); //총금액

        //json형태 데이터로 넘기기
        HashMap<String,Object> couponInfo = new HashMap<String,Object>();
        couponInfo.put("couponName",coupon.getCouponName());
        couponInfo.put("couponDiscountPrice",priceForm.getCouponDiscountPrice());
        couponInfo.put("bookingTotalPrice",priceForm.getBookingTotalPrice());

        return couponInfo;
    }

    /* 예약확인 저장 */
    @PreAuthorize("isAuthenticated()") //로그인 안하면 접근불가
    @PostMapping("/confirmation/{id}") //id=packageNum
    public String confirmation(@Validated UserBookingForm userBookingForm, BindingResult bindingResult, BookingPriceDTO priceForm,
                               @LoginUser SessionUser user, Principal principal, Model model, @PathVariable("id") Long id) {

        if(bindingResult.hasErrors()){
            return "booking-pay/booking";
        }

        //로그인 정보
        Member member;
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        //bookingTotalPrice 검증
        if(priceForm.getBookingTotalPrice() == 0){
            priceForm.setBookingTotalPrice(userBookingForm.getBookingTotalPrice());
        }

        //데이터 저장때 넘겨야할 정보 : bookingTotalPrice, Member, Package, bookingDate
        Package apackage = packageService.getPackage(id);
        userBookingForm.setBookingDate(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS")));
        String bookingDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS"));

        //데이터 저장
        userBookingService.create(userBookingForm, priceForm.getBookingTotalPrice(), bookingDate, apackage, member);

        //confirmation에 띄울 정보 
        //1.member 테이블
        model.addAttribute("member",member);

        //2.userbooking 테이블
        //임시 : bookingNum 어떻게 가져올지 고민
        long bookingNum =
                userBookingService.getBookingNum(member, bookingDate);
        UserBooking userBooking = userBookingService.getUserBooking(bookingNum);
        model.addAttribute("userBooking",userBooking);

        return "booking-pay/booking_confirmation";

    }

}
