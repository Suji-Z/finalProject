package com.project.tour.controller;

import com.project.tour.domain.MemberCreate;
import com.project.tour.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


    @GetMapping("/join")
    public String signup(MemberCreate memberCreate) {

        return "member/join";
    }

    @PostMapping("/join")
    public String signup(@Valid MemberCreate memberCreate, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
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

}
