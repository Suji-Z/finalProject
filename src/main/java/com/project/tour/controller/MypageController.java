package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.*;
import lombok.RequiredArgsConstructor;
import org.sonatype.plexus.components.sec.dispatcher.PasswordDecryptor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MemberService memberService;

    private final MypageService mypageService;

    private final PasswordEncoder passwordEncoder;

    private final WishListService wishListService;

    private final ReviewService reviewService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping (value = "/")
    public String main(Model model, Principal principal,@LoginUser SessionUser user,Pageable pageable){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        Long memberId = member.getId();


        //예약내역(예약확인 0,결제대기중 1,결제완료2)
        List<Integer> status = new ArrayList<>();

        status.add(0);
        status.add(1);
        status.add(2);

        Page<UserBooking> mypageBooking = mypageService.getMypageCancelBooking(memberId,status,pageable);
        model.addAttribute("mypageBooking",mypageBooking.getTotalElements());


        //취소내역
        List<Integer> status2 = new ArrayList<>();
        status2.add(3);
        status2.add(4);

        Page<UserBooking> mypageBookingCancle =  mypageService.getMypageCancelBooking(memberId,status2,pageable);
        model.addAttribute("mypageCancel",mypageBookingCancle.getTotalElements());

        //큐앤에이
        Page<QnA> qnaPaging = mypageService.getMypageQnA(memberId,pageable);
        model.addAttribute("qnaPaging",qnaPaging.getTotalElements());

        //고객의소리
        Page<VoiceCus> vcusPaging = mypageService.getMypageVcus(memberId,pageable);
        model.addAttribute("vcusPaging",vcusPaging.getTotalElements());

        //견적문의
        String email = member.getName();
        Page<EstimateInquiry> estPaging = mypageService.getMypageEstimate(member,pageable);
        model.addAttribute("estPaging",estPaging.getTotalElements());

        //리뷰
        List<Review> mypageReview = mypageService.getMypageReview(memberId);
        model.addAttribute("mypageReview",mypageReview.size());

        //쇼트리뷰
        List<ShortReview> mypageShortR = mypageService.getMypageShortR(memberId);
        model.addAttribute("mypageShortR",mypageShortR.size());

        //위시리스트
        List<WishList> wishList = mypageService.getWishList(member.getId());

        model.addAttribute("wishList",wishList);
        model.addAttribute("member",member);

        return "mypage/mypage_main";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/cancelList")
    public String cancel_list(Model model, Principal principal, @PageableDefault Pageable pageable,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        //예약취소된 부킹리스트 가져오기 3:예약취소 4:결제취소
        Long memberId = member.getId();

        List<Integer> status = new ArrayList<>();

        status.add(3);
        status.add(4);

        System.out.println(status);


        Page<UserBooking> mypageBookingCancle =  mypageService.getMypageCancelBooking(memberId,status,pageable);

        System.out.println(mypageBookingCancle.getTotalElements());

        model.addAttribute("mypageBookingCancle",mypageBookingCancle);

        return "mypage/mypage_bookingCancel_List";

    }
    //예약내역
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/bookingList")
    public String booking_list(Model model, Principal principal, @PageableDefault Pageable pageable,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        Long memberId = member.getId();



        Page<UserBooking> paging = mypageService.getMypageBooking(memberId,pageable);


        model.addAttribute("paging",paging);

        return "mypage/mypage_bookingList";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/coupon")
    public String coupon(Model model, Principal principal,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

            LocalDateTime time = member.getCreatedDate();

            //웰컴쿠폰 유효기간 : '가입 시기'로부터 + 3달
            LocalDateTime plusTime = time.plusMonths(3);
            model.addAttribute("plusTime",plusTime);


        }else{

            member = memberService.getName(user.getEmail());
            model.addAttribute("plusTime",null);

        }

        String couponNum = member.getCoupons();

        if(couponNum==null || couponNum.equals("")){
            model.addAttribute("mypageCoupons",null);
        }else{

            List<Coupon> coupons = mypageService.getMypageCoupon(couponNum);
            model.addAttribute("mypageCoupons",coupons);


        }




        return "mypage/mypage_coupon";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/point")
    public String point(Model model, Principal principal, @LoginUser SessionUser user){
        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        List<Pay> mypagePay = mypageService.getMypagePay(member.getId());

        int sum=0; //총 구매금액
        int sum1=0; //총 사용한 포인트

        for(int i=0;i<mypagePay.size();i++){

            int point = mypagePay.get(i).getPayTotalPrice();
            sum += point;

        }

        for(int i=0;i<mypagePay.size();i++){

            int totalUsed = mypagePay.get(i).getUsedPoint();
            sum1 += totalUsed;

        }

        int payPoint = (int)Math.round(sum*0.05); //총 구매적립포인트 계산

        //댓글등록 적립포인트 계산
        List<NoticeReply> savedPoint = mypageService.getSavedPoint(member.getId());
        int num = savedPoint.size();
        int savePoint1 = 500 * num;

        //소멸예정 포인트(댓글적립포인트)

        int point2 = 0;

        for(int k=0;k<savedPoint.size();k++){

            LocalDateTime time1 =  savedPoint.get(k).getCreated(); //댓글쓴 시간
            LocalDateTime expired =  time1.plusMonths(9); //플러스 아홉달(현재시간보다 3개월 전에 소멸예정 띄울것임)
            LocalDateTime current =  LocalDateTime.now(); //현재시간

            if(current.isAfter(expired)){ //현재시간이 포인트 적립 후 11개월 후 보다 넘은 상태라면
                point2 += 500;
            }

        }

        if(point2==0){
            model.addAttribute("expiredPoint",0);
        }else{
            model.addAttribute("expiredPoint",point2);
        }

        //소멸예정 포인트(구매적립포인트)

        int payedAmount = 0;

        for(int j=0;j<mypagePay.size();j++){

            LocalDateTime time2 = mypagePay.get(j).getPayDate(); //결제한 날짜
            LocalDateTime expired2 = time2.plusMonths(9); //플러스 아홉달
            LocalDateTime current2 = LocalDateTime.now(); //현재시간

            if(current2.isAfter(expired2)){ //현재시간이 결제 후 11개월 후 보다 넘은 상태라면
                payedAmount += mypagePay.get(j).getPayTotalPrice();
            }

        }

        if(payedAmount==0){
            model.addAttribute("expiredPayedPoint",0);
        }else{
            int expiredPoint2  = (int)Math.round(payedAmount*0.05);
            model.addAttribute("expiredPayedPoint",expiredPoint2);
        }


        //적립된 포인트 리스트
        model.addAttribute("savedPoint", savedPoint);
        model.addAttribute("savedPoint1",savePoint1);
        model.addAttribute("payPoint",payPoint);

        //사용한 포인트 합
        model.addAttribute("sum1",sum1);

        //결제내역 리스트(사용한 포인트)
        model.addAttribute("mypagePay",mypagePay);

        //회원정보
        model.addAttribute("member",member);

        return "mypage/mypage_point";

    }

    //회원정보 띄우기
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/update")
    public String update1(Model model, Principal principal, MemberCreate memberCreate,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        memberCreate.setBirth(member.getBirth());
        memberCreate.setEmail(member.getEmail());
        memberCreate.setName(member.getName());
        memberCreate.setEmail(member.getEmail());
        memberCreate.setPhone_num(member.getPhone());
        //memberCreate.setKeyword(member.getKeyword());

        //키워드 가져오기
        String keywords = member.getKeyword();

        if(keywords!=null) {
            System.out.println(keywords);
            model.addAttribute("keywords",keywords);
        }else{
            model.addAttribute("keywords","n");

        }

        model.addAttribute("social",member.getSocial());


        return "mypage/mypage_profileUpdate";

    }


    //회원정보 수정하기
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/update")
    public String update2(@Valid MemberCreate memberCreate,BindingResult bindingResult,Principal principal,@LoginUser SessionUser user){


//        if(bindingResult.hasErrors()){
//            return "mypage/mypage_profileUpdate";
//        }

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        //키워드 들어온거 확인해보기
        String keyword = memberCreate.getKeyword();
        System.out.println(keyword);

        mypageService.updateProfile(member,memberCreate.getName(),memberCreate.getBirth(), memberCreate.getKeyword(),memberCreate.getPhone_num());

        return "redirect:/mypage/update";

    }

    //비밀번호 변경 html
    @GetMapping(value = "/pwdUpdate")
    public String pwdUpdate1(PwdUpdateForm pwdUpdateForm, Model model,Principal principal,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        model.addAttribute("social",member.getSocial());

        model.addAttribute("pwdUpdateForm",pwdUpdateForm);

        return "mypage/mypage_pwdUpdate";
    }

    //비밀번호 변경하기
    @PostMapping(value = "/pwdUpdate")
    public String pwdUpdate2(@Valid PwdUpdateForm pwdUpdateForm,BindingResult bindingResult,Principal principal,@LoginUser SessionUser user) {


        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        if(bindingResult.hasErrors()){
            //System.out.println("얍");
            return "mypage/mypage_pwdUpdate";
        }



        if(!pwdUpdateForm.getPassword1().equals(pwdUpdateForm.getPassword2())){
            bindingResult.addError(new FieldError("memberCreate","password2","비밀번호가 일치하지 않습니다."));
            return "mypage/mypage_pwdUpdate";
        }
        try {

            String newPwd = pwdUpdateForm.getPassword1();

            //System.out.println(newPwd);

            String encodePWd = passwordEncoder.encode(newPwd);

            //System.out.println(encodePWd);

            mypageService.updatePwd(member, encodePWd);

        }catch (Exception e){

            System.out.println(e.toString());
        }

        return "redirect:/mypage/";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/qna")
    public String qna(Model model, Principal principal,@PageableDefault Pageable pageable,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        Long memberId = member.getId();

        Page<QnA> paging = mypageService.getMypageQnA(memberId,pageable);


        model.addAttribute("paging",paging);

        return "mypage/mypage_q&a_list";

    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/reviewList")
    public String review_list(Model model, Principal principal,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        Long memberId = member.getId();

        List<Review> mypageReview = mypageService.getMypageReview(memberId);
        List<ShortReview> mypageShortR = mypageService.getMypageShortR(memberId);

        model.addAttribute("mypageShortR",mypageShortR);

        model.addAttribute("mypageReview",mypageReview);

        //System.out.println(mypageReview.size());

        return "mypage/mypage_reviewList";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/estimateList")
    public String estimate_list(Model model, Principal principal,@PageableDefault Pageable pageable,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        String email = member.getName();

        Page<EstimateInquiry> paging = mypageService.getMypageEstimate(member,pageable);

        model.addAttribute("paging",paging);

        return "mypage/mypage_estimateList";

    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/unregister")
    public String unregisterPage(){


        return "mypage/mypage_unregister";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/unregister2")
    public String unregister(Principal principal,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        mypageService.unregister(member);

        return "redirect:/";

    }

    //고객의 소리
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/voicecusList")
    public String voicecus_list(Model model, @PageableDefault Pageable pageable,
                                Principal principal,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        Long id = member.getId();

        Page<VoiceCus> paging = mypageService.getMypageVcus(id,pageable);

        model.addAttribute("paging",paging);

        return "mypage/mypage_voicecus_list";

    }

    @PostMapping("/wishListCancel")
    public String wishListCancel(@RequestParam("packageNum") Long packageNum,
                                    Principal principal, @LoginUser SessionUser user,
                                    Model model){

        //로그인 확인
        Member member;
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        wishListService.deleteWish(member.getId(),packageNum);

        List<WishList> wishList = mypageService.getWishList(member.getId());

        System.out.println("위시리스트사이즈"+wishList.size());

        model.addAttribute("wishList",wishList);


        return "mypage/mypage_main :: #wishListTable";
    }


}
