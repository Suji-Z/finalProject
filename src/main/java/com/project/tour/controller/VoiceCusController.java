package com.project.tour.controller;

import com.project.tour.domain.Member;
import com.project.tour.domain.VoiceCus;
import com.project.tour.domain.VoiceCusForm;
import com.project.tour.domain.VoiceCusReplyForm;
import com.project.tour.oauth.dto.OAuthAttributes;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.VoiceCusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.awt.*;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/voiceCus")
public class VoiceCusController {

    private final VoiceCusService voiceCusService;
    private final MemberService memberService;

    @Autowired
    private final HttpSession httpSession;

    @RequestMapping("/list")
    public String list(Model model, @PageableDefault(size = 5) Pageable pageable){

        Page<VoiceCus> paging = voiceCusService.getList(pageable);

        model.addAttribute("paging",paging);
        return "voicecus/voicecus-list";
    }

    @RequestMapping("/article/{id}")
    public String article(Model model, @PathVariable("id") Integer id,@LoginUser SessionUser user,Principal principal){

        VoiceCus voiceCus = voiceCusService.getVoiceCus(id);

        System.out.println("회원 정보:"+principal.getName());

        String email;
        String name;

        if(memberService.existByEmail(principal.getName())){

            Member member = memberService.getName(principal.getName());
            email = member.getEmail();
            name = member.getName();

        }else {

            email = user.getEmail();
            name = user.getName();

        }

        model.addAttribute("voiceCus",voiceCus);
        model.addAttribute("email",email);
        model.addAttribute("name",name);

        return "voicecus/voicecus-article";
    }


    //고객의소리 글작성
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String voiceCusCreate(Model model, Principal principal, @LoginUser SessionUser user){


        model.addAttribute("voiceCusForm",new VoiceCusForm());

        return "voicecus/voicecus-create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String voiceCusCreate(@Valid VoiceCusForm voiceCusForm, BindingResult bindingResult,
                                 Principal principal,@LoginUser SessionUser user){

        if (bindingResult.hasErrors()){
            return "voicecus/voicecus-create";
        }

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }



        voiceCusService.create(voiceCusForm.getSubject(),voiceCusForm.getContent(),voiceCusForm.getTypes(),member);

        return "redirect:/voiceCus/list";
    }

    @GetMapping("/modify/{id}")
    public String voiceCusModify(VoiceCusForm voiceCusForm,@PathVariable("id") Integer id, Principal principal){

        VoiceCus voiceCus = voiceCusService.getVoiceCus(id);


        voiceCusForm.setSubject(voiceCus.getSubject());
        voiceCusForm.setContent(voiceCus.getContent());
        voiceCusForm.setTypes(voiceCus.getTypes());

        return "voicecus/voicecus-create";
    }

    @PostMapping("/modify/{id}")
    public String voiceCusModify(@Valid VoiceCusForm voiceCusForm,BindingResult bindingResult,
                                 @PathVariable("id") Integer id,Principal principal) {

        if (bindingResult.hasErrors()) {
            return "voicecus/voicecus-create";

        }

        VoiceCus voiceCus = voiceCusService.getVoiceCus(id);


        voiceCusService.modify(voiceCus, voiceCusForm.getSubject(), voiceCusForm.getContent(), voiceCusForm.getTypes());

        return String.format("redirect:/voiceCus/article/%s", id);
    }

    @GetMapping("/delete/{id}")
    public String voiceCusDelete(Principal principal,@PathVariable("id") Integer id){
        VoiceCus voiceCus = voiceCusService.getVoiceCus(id);

        voiceCusService.delete(voiceCus);

        return "redirect:/voiceCus/list";
    }


}
