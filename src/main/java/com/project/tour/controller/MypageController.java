package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.MypageService;
import com.project.tour.service.ReviewReplyService;
import com.project.tour.service.ReviewService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    private final MemberService memberService;

    private final MypageService mypageService;

    private final PasswordEncoder passwordEncoder;


    @PreAuthorize("isAuthenticated()")
    @GetMapping (value = "/")
    public String main(Model model, Principal principal,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

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

        member = memberService.getMember(principal.getName());

        //결제완료된 부킹리스트 가져오기
        Long memberId = member.getId();
        int status = 3; // 0:예약확인중 1:결제대기중 2:결제완료 3:예약취소

        List<UserBooking> mypageBookingCancle =  mypageService.getMypageCancelBooking(memberId,status);

        System.out.println(mypageBookingCancle.size());

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

        }else{

            member = memberService.getName(user.getEmail());

        }

        member = memberService.getMember(principal.getName());

        LocalDateTime time = member.getCreatedDate();

        //웰컴쿠폰 유효기간 : '가입 시기'로부터 + 1달
        LocalDateTime plusTime = time.plusMonths(1);


        String couponNum = member.getCoupons();
        List<Coupon> coupons = mypageService.getMypageCoupon(couponNum);

        model.addAttribute("plusTime",plusTime);
        model.addAttribute("mypageCoupons",coupons);

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

        member = memberService.getMember(principal.getName());

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

        member = memberService.getMember(principal.getName());

        String email = member.getEmail();

        Page<EstimateInquiry> paging = mypageService.getMypageEstimate(email,pageable);

        model.addAttribute("paging",paging);

        return "mypage/mypage_estimateList";

    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/unregister")
    public String unregister(){

        return "mypage/mypage_unregister";

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


}
