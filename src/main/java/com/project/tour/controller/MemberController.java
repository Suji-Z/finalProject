package com.project.tour.controller;

import com.project.tour.domain.MemberCreate;
import com.project.tour.service.MemberService;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Random;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join/emailCheck")
    public ResponseEntity<?> checkEmail(@RequestParam(value = "email") String email) throws Exception{

        if (memberService.existByEmail(email) == true) {
            throw new BadRequestException("이미 사용중인 이메일입니다.");
        } else {
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }
    }

  /*  @GetMapping("/login/searchEmail")
    public ResponseEntity<?> searchEmail(@RequestParam(value = "name") String name,String phone_num) throws  Exception {

        if(memberService.existByName(name) == true && memberService.existByPhone_num(phone_num)==true){
            throw new BadRequestException("사용자 정보가 일치합니다.");
        }
    }*/


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

    @GetMapping("/login")
    public String login(){

        return "member/login";
    }

    @GetMapping("/login/searchEmail")
    public String searchEmail(){
        return "member/forgot-email";
    }

    @GetMapping("/login/searchPassword")
    public String searchPassword(){
        return "member/forgot-password";
    }


    @GetMapping("/join/phoneCheck")
    public @ResponseBody String sendSMS(@RequestParam(value="phone_num") String phone_num) throws CoolsmsException {
        return memberService.PhoneNumberCheck(phone_num);
    }
    /*@ResponseBody
    @GetMapping("/join/phoneCheck")
    public String SMSController(@RequestParam("phone_num") String phone_num) {

        Random rand  = new Random();
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        System.out.println("수신자 번호 : " + phone_num);
        System.out.println("인증번호 : " + numStr);

        memberService.certifiedPhoneNumber(phone_num, numStr);

        return numStr;
    }*/

}
