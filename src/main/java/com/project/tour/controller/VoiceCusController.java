package com.project.tour.controller;

import com.project.tour.domain.Member;
import com.project.tour.domain.VoiceCus;
import com.project.tour.domain.VoiceCusForm;
import com.project.tour.service.MemberService;
import com.project.tour.service.VoiceCusService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.awt.*;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/voiceCus")
public class VoiceCusController {

    private final VoiceCusService voiceCusService;
    private final MemberService memberService;

    @RequestMapping("/list")
    public String list(Model model, @PageableDefault Pageable pageable){

        Page<VoiceCus> paging = voiceCusService.getList(pageable);

        model.addAttribute("paging",paging);
        return "voicecus/voicecus-list";
    }

    @RequestMapping("/article/{id}")
    public String article(Model model, @PathVariable("id") Integer id){

        VoiceCus voiceCus = voiceCusService.getVoiceCus(id);

        model.addAttribute("voiceCus",voiceCus);

        return "voicecus/voicecus-article";
    }


    //고객의소리 글작성
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String voiceCusCreate(Model model,Principal principal){

        model.addAttribute("voiceCusForm",new VoiceCusForm());
        model.addAttribute("name",principal.getName());

        return "voicecus/voicecus-create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String voiceCusCreate(@Valid VoiceCusForm voiceCusForm, BindingResult bindingResult,
                                 Principal principal){

        if (bindingResult.hasErrors()){
            return "voicecus/voicecus-create";
        }

        System.out.println("이름:"+principal.getName());

        Member member = memberService.getName(principal.getName());
       String memberName = member.getName();



        voiceCusService.create(voiceCusForm.getSubject(),voiceCusForm.getContent(),voiceCusForm.getTypes(),member);

        return "redirect:/voiceCus/list";
    }

    @GetMapping("/modify/{id}")
    public String voiceCusModify(VoiceCusForm voiceCusForm,@PathVariable("id") Integer id, Principal principal){

        VoiceCus voiceCus = voiceCusService.getVoiceCus(id);

        if(!voiceCus.getAuthor().getName().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }

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

        if (!voiceCus.getAuthor().getName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        voiceCusService.modify(voiceCus, voiceCusForm.getSubject(), voiceCusForm.getContent(), voiceCusForm.getTypes());

        return String.format("redirect:/voiceCus/article/%s", id);
    }

    @GetMapping("/delete/{id}")
    public String voiceCusDelete(Principal principal,@PathVariable("id") Integer id){
        VoiceCus voiceCus = voiceCusService.getVoiceCus(id);

        if(!voiceCus.getAuthor().getName().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
        }

        voiceCusService.delete(voiceCus);

        return "redirect:/voiceCus/list";
    }


}
