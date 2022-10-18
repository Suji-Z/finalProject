package com.project.tour.controller;

import com.project.tour.domain.Member;
import com.project.tour.domain.VoiceCus;
import com.project.tour.domain.VoiceCusReply;
import com.project.tour.domain.VoiceCusReplyForm;
import com.project.tour.service.MemberService;
import com.project.tour.service.VoiceCusReplyService;
import com.project.tour.service.VoiceCusService;
import lombok.RequiredArgsConstructor;
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
import java.security.Principal;
import java.util.List;

@RequestMapping("/reVoiceCus")
@RequiredArgsConstructor
@Controller
public class VoiceCusReplyController {

    private final VoiceCusService voiceCusService;
    private final VoiceCusReplyService voiceCusReplyService;
    private final MemberService memberService;

    @GetMapping("/article/{id}")
    public String articleReply(Model model,@PathVariable("id") Integer id){

        VoiceCus voiceCus = voiceCusService.getVoiceCus(id);
        VoiceCusReply reply = voiceCusReplyService.getReply(id);

        model.addAttribute("voiceCus",voiceCus);
        model.addAttribute("reply",reply);

        return "voicecus/voicecus-reply";

    }

    /** 글 작성 **/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/create/{id}")
    public String createReply(@PathVariable("id") Integer id,Principal principal,Model model){
        
        //답변 페이지 상단엔 기존 게시글(유저) 출력 위해 model에 정보 그대로 담아보내기
        VoiceCus voiceCus = voiceCusService.getVoiceCus(id);
        model.addAttribute("voiceCus",voiceCus);

        //답변 입력폼,로그인정보(관리자)
        model.addAttribute("voiceCusReplyForm",new VoiceCusReplyForm());
        model.addAttribute("name",principal.getName());

        return "voicecus/voicecus-reply-create";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create/{id}")
    public String createReply(Model model, @PathVariable("id") Integer id, @Valid VoiceCusReplyForm voiceCusReplyForm,
                              BindingResult bindingResult, Principal principal){

        VoiceCus voiceCus = voiceCusService.getVoiceCus(id);

        if(bindingResult.hasErrors()){
            //입력실패시, 입력폼으로 리턴
            return "voicecus/voicecus-reply-create";
        }

       voiceCusReplyService.create(voiceCus,voiceCusReplyForm.getContent());

        return "redirect:/voiceCus/list";

    }


    /** 글 수정 **/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/modify/{id}")
    public String modifyReply(VoiceCusReplyForm voiceCusReplyForm,@PathVariable("id") Integer id,
                              Principal principal,Model model){
        VoiceCusReply reply = voiceCusReplyService.getReply(id);

        voiceCusReplyForm.setContent(reply.getContent());

        VoiceCus voiceCus = reply.getVoiceCus();

        model.addAttribute("voiceCus",voiceCus);

        return "voicecus/voicecus-reply-create";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modify/{id}")
    public String modifyReply(@Valid VoiceCusReplyForm voiceCusReplyForm,BindingResult bindingResult,
                              @PathVariable("id") Integer id,Principal principal){

        if(bindingResult.hasErrors()){
            return "voicecus/voicecus-reply-create";
        }

        VoiceCusReply reply = voiceCusReplyService.getReply(id);


        voiceCusReplyService.modify(reply,voiceCusReplyForm.getContent());

        return "redirect:/voiceCus/list";
    }

    /** 글 삭제 **/
    @GetMapping("/delete/{id}")
    public String deleteReply(@PathVariable("id") Integer id,Principal principal){

        VoiceCusReply reply = voiceCusReplyService.getReply(id);

        voiceCusReplyService.delete(reply);

        return "redirect:/voiceCus/list";
    }

}
