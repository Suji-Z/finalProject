package com.project.tour.controller;

import com.project.tour.domain.MailDTO;
import com.project.tour.domain.Member;
import com.project.tour.domain.MemberCreate;
import com.project.tour.service.MailService;
import com.project.tour.service.MemberService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    private final MailService mailService;

    @GetMapping("/join/emailCheck")
    public ResponseEntity<?> checkEmail(@RequestParam(value = "email") String email) throws Exception{

        if (memberService.existByEmail(email) == true) {
            throw new BadRequestException("이미 사용중인 이메일입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }
    }



    @GetMapping("/join")
    public String signup(MemberCreate memberCreate) {

        return "member/join";
    }


    @PostMapping("/join")
    public String signup(@Valid MemberCreate memberCreate, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return "member/join";
        }

        if (memberService.existByEmail(memberCreate.getEmail()) == true) {
            bindingResult.addError(new FieldError("memberCreate","email","이미 사용중인 이메일입니다."));
            return "member/join";
        }

        //비밀번호 확인
        if(!memberCreate.getPassword1().equals(memberCreate.getPassword2())){
            bindingResult.addError(new FieldError("memberCreate","password2","비밀번호가 일치하지 않습니다."));
            return "member/join";
        }
        try {

            memberService.create(memberCreate);

        }catch (DataIntegrityViolationException e){
            e.printStackTrace();

            bindingResult.reject("signupFaild","이미 등록된 사용자입니다.");
            return "member/join";
        }catch (Exception e){
            e.printStackTrace();

            bindingResult.reject("signupFaild",e.getMessage());

            return "member/join";
        }

        return "redirect:/";
    }

    public String socialInfo(Member member){

        String info = member.getSocial();

        return info;
}

    @GetMapping("/member/login")
    public String login(){


        return "member/login";
    }

    @GetMapping("/member/login/searchEmail")
    public String searchEmail(){

        return "member/forgot-email";
    }

    @GetMapping("/member/login/searchPassword")
    public String searchPassword(){
        return "member/forgot-password";
    }


    @GetMapping("/join/phoneCheck")
    public @ResponseBody String sendSMS(@RequestParam(value="phone_num") String phone_num) throws CoolsmsException {
        return memberService.PhoneNumberCheck(phone_num);
    }

    @ResponseBody
    @GetMapping(value = "/member/login/findId")
   public String findId(@RequestParam(value = "inputName",required = false) String name,@RequestParam(value = "inputPhone",required = false) String phone) throws UnsupportedEncodingException {

        return memberService.findEmail(name,phone);
   }

   @GetMapping("/member/login/findPwd")
   @ResponseBody
    public boolean findPwd(@RequestParam("pwdEmail") String email){

        //이메일 DB에서 조회하여 반환. 있으면 true?
        return memberService.checkEmail(email);
   }

   @PostMapping("/member/login/findPwd/sendEmail")
   @ResponseBody
    public String sendEmail(@RequestParam("pwdEmail") String email){

        String tmpPassword = memberService.getTempPassword();

        memberService.updatePassword(tmpPassword,email);

        MailDTO mail = mailService.createMail(tmpPassword,email);
        mailService.sendMail(mail);

        return "member/login";
   }

}
